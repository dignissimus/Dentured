package me.ezeh.language.dentured.tokens

import me.ezeh.language.dentured.Element
import org.apache.commons.lang3.StringEscapeUtils
import java.util.regex.Pattern

open class Token(val type: Element, raw: String){
    open val value: String
    init {
        val matcher = Pattern.compile(type.regex).matcher(raw)
        if(!matcher.matches()){
            throw IllegalStateException("The string provided must match the type")
        }
        if(type.groupName!="")
            value = matcher.group(type.groupName)
        else
            value = raw

    }

    override fun toString(): String {
        return StringEscapeUtils.escapeJava("Token(${type.name}) -> $value")
    }
}