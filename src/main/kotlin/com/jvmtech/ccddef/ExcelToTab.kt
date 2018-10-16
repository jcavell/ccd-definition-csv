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

    fun convertSheetToRows(fromUntil: IntRange, numCols: Int, xssfSheet: XSSFSheet) : List<Row> {
        return xssfSheet.rowIterator().asSequence().withIndex().filter { row -> row.index >= fromUntil.first && row.index <= fromUntil.last }.map { row ->
            Row((0..numCols -1).map { colIndex ->
                val cell = row.value.getCell(colIndex)
                val cellStringValue = if(cell == null) "" else cell.toString()
                ColValue(cellStringValue) }.toList())
        }.toList()
    }

    fun convert(sourceFile: File): List<Tab> {

        val excelFile = FileInputStream(sourceFile)
        val workbook = XSSFWorkbook(excelFile)

        val tabsNames = workbook.sheetIterator().asSequence().map { it.sheetName }
        return tabsNames.map { tn ->
            val sheet = workbook.getSheet(tn)
            val colNames = convertRowToColNames(workbook.getSheet(tn).getRow(2))
            val numCols = colNames.size
            Tab(
                    tn,
                    convertSheetToRows(0..1, numCols, sheet),
                    colNames,
                    convertSheetToRows(3..9999, numCols, sheet)
            )
        }.toList()
    }
}
