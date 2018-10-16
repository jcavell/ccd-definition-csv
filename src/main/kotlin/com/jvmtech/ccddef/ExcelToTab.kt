package com.jvmtech.ccddef

import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileInputStream

class ExcelToTab {

    fun convertRowToColNames(row: XSSFRow) : List<ColName> {
        return row.cellIterator().asSequence().map { ColName(it.stringCellValue) }.toList()
    }

    fun convertSheetToRows(rowFrom: Int, rowUntil: Int, numCols: Int, xssfSheet: XSSFSheet) : List<Row> {
        return xssfSheet.rowIterator().asSequence().withIndex().filter { it.index >= rowFrom && it.index <= rowUntil }.map {
            Row((0..numCols -1).map { i ->
                val cell = it.value.getCell(i)
                val cellStringValue = if(cell == null) "" else cell.toString()
                ColValue(cellStringValue) }.toList())
        }.toList()
    }

    fun convert(sourceFile: File): List<Tab> {

        val excelFile = FileInputStream(sourceFile)
        val workbook = XSSFWorkbook(excelFile)

        val tabsNames = workbook.sheetIterator().asSequence().map { it.sheetName }
        return tabsNames.map { tn ->
            val colNames = convertRowToColNames(workbook.getSheet(tn).getRow(2))
            Tab(
                    tn,
                    convertSheetToRows(0, 1, colNames.size, workbook.getSheet(tn)),
                    colNames,
                    convertSheetToRows(3, 9999, colNames.size, workbook.getSheet(tn))
            )
        }.toList()
    }
}
