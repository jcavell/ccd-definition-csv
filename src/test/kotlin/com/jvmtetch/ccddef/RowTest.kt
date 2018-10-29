package com.jvmtetch.ccddef

import com.jvmtech.ccddef.*
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import org.mockito.MockitoAnnotations

import org.junit.jupiter.api.Assertions.assertEquals
import java.io.File
import java.util.*

internal class RowTest {

    @Test
    fun testRowWithColValIsNotEmpty(){
        val colVals = listOf(ColValue("a"), ColValue(""))
        val row = Row(colVals)
        assertThat(row.isEmpty(), equalTo(false))
    }

    @Test
    fun testRowWithAllEmptyColValIsEmpty(){
        val colVals = listOf(ColValue(""), ColValue(""))
        val row = Row(colVals)
        assertThat(row.isEmpty(), equalTo(true))
    }
}
