package com.jvmtech.ccddef

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.io.File
import java.io.FileWriter

class ExcelToCSV : CliktCommand() {
    private val source by argument().file(exists = true)
    private val dest by argument().file(exists = true)
    // val env: String by option(help="The environment").prompt("Environment")

    override fun run() {
        ExcelToCSV().convert(source, dest)
    }

    fun convert(sourceFile: File, outputDir: File){
        val workbookName = "workbook"
        val tabs = ExcelToTab().convert(sourceFile)
        TabToCSV().convert(outputDir, tabs)

        val outputFile = File(outputDir, workbookName + ".csv")
        val fileWriter = FileWriter(outputFile)
        val csvPrinter = CSVPrinter(fileWriter, CSVFormat.DEFAULT)

        csvPrinter.printRecord(tabs.map { it.name })

        fileWriter.flush()
        fileWriter.close()
        csvPrinter.close()
    }
}

fun main(args: Array<String>) = ExcelToCSV().main(args)
