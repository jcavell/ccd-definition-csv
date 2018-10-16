package com.jvmtetch.ccddef

import com.jvmtech.ccddef.*
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import org.mockito.MockitoAnnotations
import java.io.File

internal class ExcelToTabTest {

    lateinit var excelToTab: ExcelToTab

    @BeforeEach
    fun init() {
        MockitoAnnotations.initMocks(this)
        excelToTab = ExcelToTab()
    }

    @Test
    fun testConvertFromWorkbook() {

        val tab1DescriptionRow1 = Row(listOf(ColValue("desc1A"), ColValue("desc1B")))
        val tab2DescriptionRow1 = Row(listOf(ColValue("desc2A"), ColValue("desc2B")))
        val descriptionRows = listOf(tab1DescriptionRow1, tab2DescriptionRow1)

        val tab1ColNames = listOf(ColName("foo"), ColName("bar"))
        val tab1Row1 = Row(listOf(ColValue("foo1"), ColValue("bar1")))
        val tab1Row2 = Row(listOf(ColValue("foo2"), ColValue("bar2")))
        val tab1DataRows = listOf(tab1Row1, tab1Row2)
        val tab1 = Tab("MyTab1", descriptionRows, tab1ColNames, tab1DataRows)


        val tab2ColNames = listOf(ColName("moo"), ColName("tar"))
        val tab2Row1 = Row(listOf(ColValue("moo1"), ColValue("tar1")))
        val tab2Row2 = Row(listOf(ColValue("moo2"), ColValue("tar2")))
        val tab2DataRows = listOf(tab2Row1, tab2Row2)
        val tab2 = Tab("MyTab2", descriptionRows, tab2ColNames, tab2DataRows)

        val convertedTabs = excelToTab.convert(File("src/test/resources/excelToTab/ExcelWorkbook.xlsx"))

        assertThat(convertedTabs, equalTo(listOf(tab1, tab2)))
    }
}
