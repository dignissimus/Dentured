package me.ezeh.language.dentured

import org.apache.commons.cli.*
import java.io.File

fun main(args: Array<String>) {
    val options = Options()
    options.addOption("f", "file", true, "The input file")
    options.addOption("d", "demo", false, "Execute the demonstration programme")
    // println(Lexer(demo).lex())
    val parser = BasicParser()
    val line = parser.parse(options, args)
    if (line.hasOption("d")) {
        val demoCode = ClassLoader.getSystemClassLoader().getResource("demo.dt").readText()
        Parser(Lexer(demoCode).lex()).execute()
        System.exit(0)
    } else if (!line.hasOption("f")) {
        println("Please enter the file you would like to be executed")
    }
    val fileName = line.getOptionValue("-f")
    val file = File(fileName)
    val code = file.readText()
    Parser(Lexer(code).lex()).execute()
    System.exit(0)
}

