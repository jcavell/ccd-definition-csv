package com.jvmtetch.ccddef

import com.jvmtech.ccddef.*
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import org.mockito.MockitoAnnotations
import java.io.File

internal class ExcelToCSVTest {

    @Test
    fun testConvertFromWorkbook() {
      ExcelToCSV().convert(File("src/test/resources/excelToCSV/ExcelWorkbook.xlsx"), File("src/test/resources/excelToCSV/"))
    }
}
