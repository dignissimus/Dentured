package me.ezeh.language.dentured.tokens

import me.ezeh.language.dentured.DenturedExecutable
import me.ezeh.language.dentured.DenturedLambdaExecutor
import me.ezeh.language.dentured.Element.*
import me.ezeh.language.dentured.Lexer
import org.apache.commons.lang3.StringEscapeUtils
import java.util.regex.Pattern


class MethodToken(raw: String) : Token(METHOD, raw), DenturedExecutable {

    override fun execute(vararg args: Token): Token {
        if (methodName in Companion.builtin) {
            return if (!hasLambda)
                Companion.builtin[methodName]!!.execute(*this.args.toTypedArray())
            else
                (Companion.builtin[methodName]!! as DenturedLambdaExecutor).execute(*this.args.toTypedArray(), lambda = lambda)

        } else {
            val method = Companion.definedMethods[methodName] ?: throw RuntimeException("Method '$methodName' does not exist")
            return method.execute(*this.args.toTypedArray())
        }
    }

    val methodName = value
    var args: List<Token>
    val hasLambda: Boolean
    val lambda: LambdaToken

    init {
        val matcher = Pattern.compile(METHOD.regex).matcher(raw)
        matcher.find()
        args = Lexer(matcher.group("args") ?: "").lex()
        hasLambda = matcher.group("lambda") != null
        lambda = if (hasLambda) LambdaToken(matcher.group("lambda")) else LambdaToken.empty
    }

    override fun toString(): String {
        return StringEscapeUtils.escapeJava("Token(METHOD) -> $methodName:$args" + if (hasLambda) "{${lambda.value}}" else "")
    }

    companion object {
        fun defineMethod(name: String, declaration: MethodDeclarationToken) {
            definedMethods.put(name, declaration)
        }

        private val builtin = mapOf("println!" to object : DenturedExecutable {
            override fun execute(vararg args: Token): Token {
                println(args.map { if (it !is DenturedExecutable) it.value else it.execute().value }.joinToString(" "))
                return Void()
            }
        }, "print!" to object : DenturedExecutable {
            override fun execute(vararg args: Token): Token {
                print(args.map { if (it !is DenturedExecutable) it.value else it.execute().value }.joinToString(" "))
                return Void()
            }

        }, "input" to object : DenturedExecutable {
            override fun execute(vararg args: Token): Token {
                return Token(STRING, readLine() ?: "")
            }
        }, "if" to object : DenturedLambdaExecutor {
            override fun execute(vararg args: Token, lambda: LambdaToken): Token {
                if (args[0].type == BOOLEAN && args[0].value == "true")
                    return lambda.execute()
                else return Void()
            }

        }, "run" to object : DenturedLambdaExecutor {
            override fun execute(vararg args: Token, lambda: LambdaToken): Token {
                return lambda.execute()
            }

        }, "input!" to object : DenturedLambdaExecutor {
            override fun execute(vararg args: Token, lambda: LambdaToken): Token {
                return StringToken(readLine() ?: "")
            }
        })
        private val definedMethods = HashMap<String, MethodDeclarationToken>()
    }
}