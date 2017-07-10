package me.ezeh.language.dentured.tokens

import me.ezeh.language.dentured.Element.*

class PropertyToken(raw: String): Token(PROPERTY, raw){
    val propertyName = super.value
    override val value = getValue(propertyName)
    override fun toString(): String {
        return "Token(PROPERTY) -> $propertyName = $value"
    }
    companion object {
        fun getValue(property: String): String{
            return System.getProperty(property)?: Void().toString()
        }
    }
}
