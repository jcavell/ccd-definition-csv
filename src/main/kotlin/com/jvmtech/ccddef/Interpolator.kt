package com.jvmtech.ccddef

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

object MappingFromCSV{

    fun get(keyValueFile : File, env: String): Map<String, String>  {

        val fileReader = BufferedReader(FileReader(keyValueFile))
        val csvParser = CSVParser(fileReader, CSVFormat.DEFAULT.withTrim().withFirstRecordAsHeader().withIgnoreHeaderCase())

        val mapping = hashMapOf(* csvParser.getRecords().filter { it.get("env") == env }.map { it.get("key") to  it.get("value") }.toTypedArray())

        fileReader.close()
        csvParser.close()

        return mapping
    }
}

class Interpolator(env: String, version: String, val m: Map<String, String>) {
    var mapping:Map<String, String>
    init{
        mapping = hashMapOf("ENV" to env, "VERSION" to version, * m.entries.map { e -> e.key to e.value }.toTypedArray())
    }

    fun interpolate(tabName: String, keyOrLiteral: String) : String{

        var interpolated = keyOrLiteral
        if(interpolated.contains("@@.+@@".toRegex())) {
            mapping.entries.filter { it.key.contains("ENV|VERSION".toRegex()) || it.key.startsWith(tabName + ".") }.forEach {
                val colKey = it.key.replace(tabName + ".", "")
                interpolated = interpolated.replace("@@" + colKey + "@@", it.value)
            }
        }
        return interpolated;
    }
}
