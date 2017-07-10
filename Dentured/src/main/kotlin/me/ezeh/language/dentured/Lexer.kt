package me.ezeh.language.dentured

import java.util.regex.Pattern
import me.ezeh.language.dentured.Element.*
import me.ezeh.language.dentured.tokens.MethodDeclarationToken
import me.ezeh.language.dentured.tokens.MethodToken
import me.ezeh.language.dentured.tokens.PropertyToken
import me.ezeh.language.dentured.tokens.Token


class Lexer(val code: String) {
    val ignored = listOf(WHITESPACE, COMMENT, TERMINATOR)
    var lexed: List<Token>? = null
    fun lex(): List<Token> {
        if(lexed!=null){
            return lexed as List<Token>
        }
        val regexBuilder = StringBuilder()
        for(element in Element.values()){
            regexBuilder.append("|(?<${element.name}>${element.regex})")
        }
        val tokenPattern = Pattern.compile(regexBuilder.substring(1))
        // println(tokenPattern.pattern());System.exit(0)
        val matcher = tokenPattern.matcher(code)
        val tokens = ArrayList<Token>()
        while(matcher.find()){
            for (element in Element.values()){
                if(element !in ignored && matcher.group(element.name)!=null){
                    val matched = matcher.group(element.name)
                    tokens.add(when(element){
                        METHOD -> MethodToken(matched)
                        PROPERTY -> PropertyToken(matched)
                        METHODDECLARATION -> MethodDeclarationToken(matched)
                        else -> Token(element, matched)
                    })
                }
            }
            /*Element.values()
                    .filter { it !in ignores && matcher.group(it.name)!=null } // if not ignored and if matcher has matched it
                    .mapTo(tokens) { Token(it, matcher.group(it.name)) } // Add to `tokens` an Token generated using they type and the string matched
                    */
        }
        lexed = tokens
        return tokens
    }
}