package com.jvmtetch.ccddef

import com.jvmtech.ccddef.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import org.mockito.MockitoAnnotations
import java.io.File

internal class TabToCSVTest {

    lateinit var tabToCSV: TabToCSV

    @BeforeEach
    fun init() {
        MockitoAnnotations.initMocks(this)
        tabToCSV = TabToCSV()
    }

    @Test
    fun testCreateCSV() {
        val tab1DescriptionRow1 = Row(listOf(ColValue("desc1A"), ColValue("desc1B")))
        val tab2DescriptionRow1 = Row(listOf(ColValue("desc2A"), ColValue("desc2B")))
        val descriptionRows = listOf(tab1DescriptionRow1, tab2DescriptionRow1)

        val colNames = listOf(ColName("foo"), ColName("bar"))
        val row1 = Row(listOf(ColValue("foo1"), ColValue("bar1")))
        val row2 = Row(listOf(ColValue("foo2"), ColValue("bar2")))
        val dataRows = listOf(row1, row2)
        val tab = Tab("ConvertedTab1", descriptionRows, colNames, dataRows)
        tabToCSV.convert(File("src/test/resources/tabToCSV/"), listOf(tab))

        //assertThat(csvToTab.headerMapToHeaderList(hm), equalTo(listOf("z", "g", "a")))
    }
}
