package me.ezeh.language.dentured

import me.ezeh.language.dentured.tokens.Token

@FunctionalInterface
interface DenturedExecutable {
    fun execute(vararg args: Token): Token
}