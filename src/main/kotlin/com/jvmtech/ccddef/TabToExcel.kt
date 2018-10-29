package com.jvmtech.ccddef

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class TabToExcel {

    val workbook = XSSFWorkbook()

     fun sheet(tab: Tab) {
        val sheet = workbook.createSheet(tab.name)

         val createHelper = workbook.creationHelper
         val dateCellStyle = workbook.createCellStyle()
         dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));

        // Description rows
        for ((rowIndex, rowValue) in tab.descriptionRows.withIndex()) {
            val row = sheet.createRow(rowIndex)
            for ((colIndex, colValue) in rowValue.colValues.withIndex().filter { it.index < tab.colNames.size }) {
                if(colValue.value.isNotEmpty()) row.createCell(colIndex).setCellValue(colValue.value)
            }
        }

        // Column names row
        val headerRow = sheet.createRow(2)
        for ((index, colName) in tab.colNames.withIndex()) {
            val cell = headerRow.createCell(index)
            val typeAndName = typeAndName(colName)
            val cellValue = if(typeAndName.size ==2){typeAndName[1]} else{colName.name}
            if(cellValue.isNotEmpty()) cell.setCellValue(cellValue)
        }

        // Data rows
        for ((rowIndex, rowValue) in tab.dataRows.filter { !it.isEmpty()}.withIndex()) {
            val row = sheet.createRow(rowIndex + 3)
            for ((colIndex, colValue) in rowValue.colValues.withIndex().filter { !it.value.isEmpty() && it.index < tab.colNames.size }) {

                try {
                    val valueToInsert = cellValue(tab.colNames[colIndex], colValue)
                    when (valueToInsert) {
                        is String -> row.createCell(colIndex).setCellValue(valueToInsert)
                        is Double -> row.createCell(colIndex).setCellValue(valueToInsert)
                        is Date -> {
                            val cell = row.createCell(colIndex)
                            cell.setCellStyle(dateCellStyle)
                            cell.setCellValue(valueToInsert)
                        }
                        else -> throw RuntimeException("Unrecognised type")
                    }
                } catch (e: RuntimeException) {
                    throw(RuntimeException("Could not insert TAB ${tab.name} ROW ${rowIndex} COL ${colIndex} COLNAME ${tab.colNames[colIndex].name} COLVALUE ${colValue.value}", e))
                }
            }
        }
    }

    fun convert(destDir: File, name: String, tabs: List<Tab>) {

        for (tab in tabs) sheet(tab)

        val fileOut = FileOutputStream(File(destDir, name + ".xlsx"))
        workbook.write(fileOut)
        fileOut.close()
        workbook.close()
    }

    private fun typeAndName(colName: ColName) : List<String>{
        return colName.name.split(":")
    }

    fun cellValue(colName: ColName, colValue: ColValue): Any {
        val typeAndName = typeAndName(colName)

        return if (colValue.value.isEmpty()) ""
        else if (typeAndName.size < 2) colValue.value
        else if (typeAndName[0] == "Number") colValue.value.toDouble()
        else if (typeAndName[0] == "Date") toDate(colValue.value)
        else throw RuntimeException("Bad type in column name. Accepted values are Number and Date")
    }

    val yyyyDate = ".*\\d{4}$".toRegex()
    fun matchesyyyy(dateString: String) : Boolean{
        return yyyyDate.matches(dateString)
    }

    // Example 01-Jan-2018
    val formatteryyyy = DateTimeFormatter.ofPattern("d-MMM-yyyy")
    val formatteryy = DateTimeFormatter.ofPattern("d-MMM-yy")
    fun toDate(dateString: String) : Date {
        val formatter = if(matchesyyyy(dateString)) formatteryyyy else formatteryy
        return Date.from(LocalDate.parse(dateString, formatter).atStartOfDay(ZoneId.systemDefault()).toInstant())
    }
}
