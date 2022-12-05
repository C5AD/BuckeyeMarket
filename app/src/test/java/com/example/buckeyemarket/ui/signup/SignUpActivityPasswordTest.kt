package com.example.buckeyemarket.ui.signup

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test


internal class SignUpActivityPasswordTest {

    @Test
    fun passwordTestCase_short_num_returnFalse() {
        val result = SignUpTest.validateLengthPassword("123456")
        assertFalse(result)
    }

    @Test
    fun passwordTestCase_long_num_returnTrue() {
        val result = SignUpTest.validateLengthPassword("123456789")
        assertTrue(result)
    }
    @Test
    fun passwordTestCase_verylong_num_returnTrue() {
        val result = SignUpTest.validateLengthPassword("123456789101112134165546541321321321232132123")
        assertTrue(result)
    }
    @Test
    fun passwordTestCase_long_str_returnTrue() {
        val result = SignUpTest.validateLengthPassword("aldkbvjalksjvl")
        assertTrue(result)
    }
    @Test
    fun passwordTestCase_verylong_str_returnTrue() {
        val result = SignUpTest.validateLengthPassword("aldkbvjalksjvlalskdjalskdjlaskdjlkasjd")
        assertTrue(result)
    }
    @Test
    fun passwordTestCase_empty_returnFalse() {
        val result = SignUpTest.validateLengthPassword("")
        assertFalse(result)
    }

}