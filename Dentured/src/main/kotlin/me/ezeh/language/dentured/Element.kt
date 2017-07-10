package me.ezeh.language.dentured

enum class Element(val regex: String, val groupName: String = "") {
    // property is a keyword
    WHITESPACE("\\s+"),
    NUMBER("-?[0-9]+"), STRING("\"(?<text>.*?)\"", "text"), VOID("void"), PROPERTY("property\\.(?<property>[A-z\\_]+(?:\\.[A-z\\_]+)*)", "property"), VARIABLE("\\$(?<variablename>[A-z]+)", "variablename"), BOOLEAN("true|false"), TERMINATOR("[\\r\\n;$]+"), METHODDECLARATION("fn\\s+(?<methodname>[A-z]+?)\\s*(?<methodargs>\\([\\s\\S]*?\\))\\s*\\{(?<methodcode>[\\s\\S]*?)\\}", "methodname"), METHOD("(?<method>[A-z]+[!?]?)(\\((?<args>.*)\\))?\\s*(?<lambda>\\{[\\s\\S]*?\\})?", "method"), LAMBDA("\\{(?<code>[\\s\\S]*?)\\}", "code"), COMMENT("\\/\\/.*\\n?")
}