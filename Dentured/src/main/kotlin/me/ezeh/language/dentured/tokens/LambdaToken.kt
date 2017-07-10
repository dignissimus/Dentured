package me.ezeh.language.dentured.tokens

import me.ezeh.language.dentured.DenturedExecutable
import me.ezeh.language.dentured.Element
import me.ezeh.language.dentured.Lexer
import me.ezeh.language.dentured.Parser

class LambdaToken(rawCode: String): Token(Element.LAMBDA, rawCode ), DenturedExecutable {
    override fun execute(vararg args: Token): Token {
        return Parser(Lexer(value).lex()).execute()
    }
    companion object {
        val empty=LambdaToken("{}")
    }

}