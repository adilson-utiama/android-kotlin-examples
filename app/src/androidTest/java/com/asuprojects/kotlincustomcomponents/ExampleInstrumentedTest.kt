package com.asuprojects.kotlincustomcomponents

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.asuprojects.kotlincustomcomponents.helpers.ValueConversionHelper
import com.asuprojects.kotlincustomcomponents.helpers.ValueConversionHelper.Companion.UNIDADE_IN

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.asuprojects.kotlincustomcomponents", appContext.packageName)
    }

    @Test
    fun testMetodoConverter() {

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val width = 720f
        val height = 1188f

        val inchWidth = ValueConversionHelper.converter(appContext, UNIDADE_IN ,width)
        val inchHeight = ValueConversionHelper.converter(appContext, UNIDADE_IN ,height)

        assertEquals(10f, inchWidth)
        assertEquals(16.5f, inchHeight)
    }
}
