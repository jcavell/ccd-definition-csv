package com.jvmtetch.ccddef

import com.jvmtech.ccddef.Interpolator
import com.jvmtech.ccddef.MappingFromCSV
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import java.io.File

class InterpolatorTest {

    lateinit var interpolator: Interpolator

    @BeforeEach
    fun init(){
        val mapping = hashMapOf("MyTab.foo" to "bar")
        val env = "AAT"
        val version = "1.2.3"
        interpolator = Interpolator(env, version, mapping)
    }

    @Test
    fun testMappingFromCSV(){
        val mapping = MappingFromCSV.get(File("src/test/resources/interpolator/keyValues.csv"), "AAT")

        assertThat(mapping.get("CaseType.PrintableDocumentsUrl"), equalTo("http://ccd-data-store-api-aat.service.core-compute-aat.internal/callback/jurisdictions/SSCS/case-types/Benefit/documents"))
    }


    @Test
    fun testInterpolationOfExactKey(){
        assertThat(interpolator.interpolate("MyTab", "@@foo@@"), equalTo("bar"))
    }

    @Test
    fun testInterpolationOfVersion(){
        assertThat(interpolator.interpolate("MyTab", "@@VERSION@@"), equalTo("1.2.3"))
    }

    @Test
    fun testInterpolationOfKeyInMiddle(){
        assertThat(interpolator.interpolate("MyTab", "this_is @@foo@@ again"), equalTo("this_is bar again"))
    }


    @Test
    fun testInterpolationOfMultipleKeys(){
        assertThat(interpolator.interpolate("MyTab", "SSCS Case v@@VERSION@@_@@ENV@@"), equalTo("SSCS Case v1.2.3_AAT"))
    }
}
