package com.jvmtetch.ccddef

import com.jvmtech.ccddef.CSVToExcel
import org.junit.jupiter.api.Test
import java.io.File

internal class CSVToExcelTest {

    @Test
    fun testConvert() {
        CSVToExcel().convert(
                File("src/test/resources/csvToExcel/"), File("src/test/resources/csvToExcel/"), "workbook", File("src/test/resources/csvToExcel/keyValues.csv"), "AAT", "1.3.5")
    }
}
