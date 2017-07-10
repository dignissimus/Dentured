package me.ezeh.language.dentured.tokens

import me.ezeh.language.dentured.DenturedExecutable
import me.ezeh.language.dentured.Element.METHODDECLARATION
import me.ezeh.language.dentured.Element.VARIABLE
import me.ezeh.language.dentured.Lexer
import me.ezeh.language.dentured.Parser

class MethodDeclarationToken(raw: String) : Token(METHODDECLARATION, raw), DenturedExecutable {
    val methodName = value
    val rawArgs: String
    val args: List<Token>
    val rawCode: String
    val code: List<Token>

    init {
        val matcher = type.regex.toPattern().matcher(raw)
        matcher.find()
        rawArgs = matcher.group("methodargs") ?: ""
        args = Lexer(rawArgs).lex()
        rawCode = matcher.group("methodcode") ?: ""
        code = Lexer(rawCode).lex()
        MethodToken.defineMethod(methodName, this)
    }

    override fun execute(vararg args: Token): Token {
        val valMap = HashMap<String, Token>()
        for ((index, argument) in this.args.withIndex()) {
            valMap.put(argument.value, args[index])
        }
        return Parser(code.map {
            if (it.type == VARIABLE) {
                valMap[it.value] ?: it
            } else if (it is MethodToken) {
                it.args = it.args.map { if (it.type == VARIABLE) valMap[it.value] ?: it else it }
                it
            } else {
                it
            }
        }).execute()
    }

}