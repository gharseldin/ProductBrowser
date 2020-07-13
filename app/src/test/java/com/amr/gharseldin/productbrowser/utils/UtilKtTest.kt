package com.amr.gharseldin.productbrowser.utils

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.objenesis.instantiator.util.UnsafeUtils

class UtilKtTest {

    private val price = "$18.99"

    @Test
    fun getUnitsTest() {
        assertEquals("18", getUnits(price))
    }

    @Test
    fun getDecimalsTest() {
        assertEquals("99", getDecimals(price))
    }
}