package com.jvmtech.ccddef

import java.io.BufferedReader
import java.io.FileReader
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.File

class CSVToTab(val env: String, val version: String, val sourceDir: File, val workbookName: String, val keyValueFile: File) {

    var interpolator: Interpolator
    init{
        val mapping = MappingFromCSV.get(keyValueFile, env)
        interpolator = Interpolator(env, version, mapping)
    }

    fun importTabCSV(tabName: String): Tab {
        val fileReader = BufferedReader(FileReader(File(sourceDir, tabName + ".csv")))
        val csvParser = CSVParser(fileReader, CSVFormat.DEFAULT.withTrim())

        val records = csvParser.getRecords()
        val descriptionRows =
                records.withIndex().filter { it.index < 2 } .map { r -> Row(r.value.toList().map { c -> ColValue(c) }) }
        val colNames = records.withIndex().filter { it.index == 2 } .map { it.value }[0].filter{it.isNotEmpty()}.map{c -> ColName(c)}
        val dataRows =  records.withIndex().filter { it.index > 2 } .map { r -> Row(r.value.toList().map { c -> ColValue(interpolator.interpolate(tabName, c)) }) }

        fileReader.close()
        csvParser.close()

        return Tab(tabName, descriptionRows, colNames, dataRows)
    }

    fun extractTabNames(): List<String> {
        val fileReader = BufferedReader(FileReader(File(sourceDir, workbookName + ".csv")))
        val csvParser = CSVParser(fileReader, CSVFormat.DEFAULT.withTrim())

        val record = csvParser.getRecords().getOrNull(0)

        fileReader.close()
        csvParser.close()

        return if (record == null) listOf<String>() else record.toList()
    }

    fun createTabs(): List<Tab> {
        return extractTabNames().map { tn -> importTabCSV(tn) }
    }
}
