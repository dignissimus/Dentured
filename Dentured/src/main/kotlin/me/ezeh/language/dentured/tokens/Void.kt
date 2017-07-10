package me.ezeh.language.dentured.tokens

import me.ezeh.language.dentured.Element.VOID

class Void: Token(VOID, "void"){
    override fun toString() = "void"
}