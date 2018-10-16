package com.jvmtech.ccddef

import java.io.FileWriter
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.io.File

class TabToCSV {

    fun convert(outputDir: File, tabs: List<Tab>) {

        for (tab in tabs) {
            val fileWriter = FileWriter(File(outputDir, tab.name + ".csv"))
            val csvPrinter = CSVPrinter(fileWriter, CSVFormat.DEFAULT)
            tab.descriptionRows.forEach { csvPrinter.printRecord(it.colValues.map { col -> col.value }) }
            csvPrinter.printRecord(tab.colNames.map { it.typedName() })
            tab.dataRows.forEach { csvPrinter.printRecord(it.colValues.map { col -> col.value }) }

            fileWriter.flush()
            fileWriter.close()
            csvPrinter.close()
        }
    }
}
