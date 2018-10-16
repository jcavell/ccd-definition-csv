package com.jvmtetch.ccddef

import com.jvmtech.ccddef.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import org.mockito.MockitoAnnotations

import org.junit.jupiter.api.Assertions.assertEquals
import java.io.File
import java.util.*

internal class TabToExcelTest {

    lateinit var tabToExcel: TabToExcel

    @BeforeEach
    fun init() {
        MockitoAnnotations.initMocks(this)
        tabToExcel = TabToExcel()
    }

    @Test
    fun testCreateWorkbook() {

        val colNames = listOf(ColName("foo"), ColName("bar"))
        val row1 = Row(listOf(ColValue("foo1"), ColValue("bar1")))
        val row2 = Row(listOf(ColValue("foo2"), ColValue("bar2")))
        val dataRows = listOf(row1, row2)
        val tab = Tab("MyTab", listOf(), colNames, dataRows)
        tabToExcel.convert(File("src/test/resources/tabToExcel/"), "ConvertedTestWorkbook", listOf(tab))

        //assertThat(csvToTab.headerMapToHeaderList(hm), equalTo(listOf("z", "g", "a")))
    }

    @Test
    fun testExtractIntExelNameAndValue(){
        val colName = ColName("Int:MyInteger")
        val colValue = ColValue("1")

        assertEquals(1.0, tabToExcel.cellValue(colName, colValue))

    }

    @Test
    fun testExtractNoTypeDefinedExelNameAndValue(){
        val colName = ColName("MyString")
        val colValue = ColValue("1")

        assertEquals("1", tabToExcel.cellValue(colName, colValue))

    }

    @Test
    fun testConvertDate(){
        val converted =  tabToExcel.toDate("12-Feb-2018")
        val correctDate = GregorianCalendar(2018, 1, 12).time
        assertEquals(correctDate, converted)
    }

    @Test
    fun testMatches(){
        val dateString = "01-10-2018"
        assertEquals(true, tabToExcel.matchesyyyy(dateString))
    }

}
