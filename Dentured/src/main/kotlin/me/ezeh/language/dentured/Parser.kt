package me.ezeh.language.dentured

import me.ezeh.language.dentured.tokens.MethodToken
import me.ezeh.language.dentured.tokens.Token
import me.ezeh.language.dentured.tokens.Void


class Parser(val tokens: List<Token>) {
    var lastReturn: Token = Void()
    fun execute(): Token {
        for(token in tokens){
            if(token is MethodToken){
                lastReturn = token.execute()
            }
            else lastReturn = token
        }
        return lastReturn
    }
}