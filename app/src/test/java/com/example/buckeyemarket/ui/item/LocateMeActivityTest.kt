package com.example.buckeyemarket.ui.item

import com.example.buckeyemarket.ui.signup.SignUpTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test


internal class SignUpActivityEmailDomainTest {
    @Test
    fun locateMeTestCase_NotEmpty_returnTrue() {
        val result = LocateMeTest.validateAddress("123 William Street")
        assertTrue(result)
    }

    @Test
    fun locateMeTestCase_IsEmpty_returnFalse() {
        val result = LocateMeTest.validateAddress("")
        assertFalse(result)
    }
}
