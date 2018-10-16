package com.jvmtetch.ccddef

import com.jvmtech.ccddef.*
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import org.mockito.MockitoAnnotations
import java.io.File

internal class CSVToTabTest {

    lateinit var csvToTab: CSVToTab

    @BeforeEach
    fun init() {
        MockitoAnnotations.initMocks(this)
        csvToTab = CSVToTab("AAT", "1.4.7", File("src/test/resources/csvToTab/"), "tabs", File("src/test/resources/csvToTab/keyValues.csv"))
    }

    @Test
    fun testParseTabsCSV(){
        val tabs = csvToTab.createTabs()
        assertThat(tabs[0].name, equalTo("Jurisdiction"))
        assertThat(tabs[1].name, equalTo("CaseType"))
    }

    @Test
    fun testParseSingleCSV() {
        val tab = csvToTab.importTabCSV( "Jurisdiction")
        assertThat(tab.name, equalTo("Jurisdiction"))

        assertThat(tab.colNames, equalTo(listOf(
                ColName("LiveFrom"),
                ColName("LiveTo"),
                ColName("ID"),
                ColName("Name"),
                ColName("Description"))))

        assertThat(tab.dataRows, equalTo(listOf(Row(listOf(
                ColValue("01/01/2018"),
                ColValue(""),
                ColValue("SSCS"),
                ColValue("Tribunals"),
                ColValue("Social Security and Child Support"))))))
    }

    @Test
    fun testExtractTabs() {
        val fields = csvToTab.extractTabNames()
        assertThat(fields[0], equalTo("Jurisdiction"))
    }

}
