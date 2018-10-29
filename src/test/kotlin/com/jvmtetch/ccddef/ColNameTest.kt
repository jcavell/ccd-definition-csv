package com.jvmtetch.ccddef

import com.jvmtech.ccddef.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import org.mockito.MockitoAnnotations

import org.junit.jupiter.api.Assertions.assertEquals
import java.io.File
import java.util.*

internal class ColNameTest {

    @Test
    fun testColNameShouldNotBeTyped(){
        val colName = ColName("foo")
        assertEquals("foo", colName.typedName())
    }

    @Test
    fun testColNameShouldBeDateTyped(){
        assertEquals("Date:LiveFrom", ColName("LiveFrom").typedName())
        assertEquals("Date:LiveTo", ColName("LiveTo").typedName())
    }

    @Test
    fun testColNameShouldBeIntTyped(){
        assertEquals("Number:DisplayOrder", ColName("DisplayOrder").typedName())
        assertEquals("Number:PageFieldDisplayOrder", ColName("PageFieldDisplayOrder").typedName())
        assertEquals("Number:PageColumnNumber", ColName("PageColumnNumber").typedName())

    }
}
