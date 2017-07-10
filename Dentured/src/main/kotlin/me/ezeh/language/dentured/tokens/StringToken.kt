package me.ezeh.language.dentured.tokens

import me.ezeh.language.dentured.Element.STRING

class StringToken(string: String): Token(STRING, "\"\"") {
    override val value = string
}