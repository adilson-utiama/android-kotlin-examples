package com.asuprojects.kotlincustomcomponents

import com.asuprojects.kotlincustomcomponents.helpers.ValueConversionHelper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

class ValueConversionHelperTest {

    @Test
    fun testConversaoPTparaINCH() {

        val width = 720
        val height = 1188

        val inchWidth = ValueConversionHelper.convertPTtoINCH(width.toFloat())
        val inchHeight = ValueConversionHelper.convertPTtoINCH(height.toFloat())

        Assert.assertEquals(10f, inchWidth)
        Assert.assertEquals(16.5f, inchHeight)

    }


}