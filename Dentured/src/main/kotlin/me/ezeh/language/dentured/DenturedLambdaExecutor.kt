package me.ezeh.language.dentured

import me.ezeh.language.dentured.tokens.LambdaToken
import me.ezeh.language.dentured.tokens.Token

interface DenturedLambdaExecutor: DenturedExecutable {
    override fun execute(vararg args: Token): Token {
        return execute(*args, lambda = LambdaToken.empty )
    }
    fun execute(vararg args: Token, lambda: LambdaToken): Token
}