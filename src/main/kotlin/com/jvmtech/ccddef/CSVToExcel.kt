package com.jvmtech.ccddef

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import java.io.File

class CSVToExcel : CliktCommand() {
    private val sourcedir by argument().file(exists = true)
    private val destdir by argument().file(exists = true)
    private val workbookName by argument()
    private val keyValueFile by argument().file(exists = true)
    private val env by argument()
    private val version by argument()

    // val env: String by option(help="The environment").prompt("Environment")

    override fun run() {
        convert(sourcedir, destdir, workbookName, keyValueFile, env, version)
    }

    fun convert(sourceDir: File, destDir: File, workbookName :String, keyValueFile: File, env: String, version: String) {
        val tabs = CSVToTab(env, version, sourceDir, workbookName, keyValueFile).createTabs()
        TabToExcel().convert(destDir, workbookName, tabs)
    }
}

fun main(args: Array<String>) = CSVToExcel().main(args)


