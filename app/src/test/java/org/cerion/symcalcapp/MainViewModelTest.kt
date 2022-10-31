package org.cerion.symcalcapp

import org.junit.Assert.assertEquals
import org.junit.Test


internal class MainViewModelTest {
    private val viewModel = MainViewModel()

    private val display
        get() = viewModel.display

    private val preview
        get() = viewModel.preview

    private fun onKey(key: Key) {
        viewModel.onKey(key)
    }

    private fun onKeys(vararg keys: Key) {
        keys.forEach {
            viewModel.onKey(it)
        }
    }

    @Test
    fun basic() {
        assertEquals("", display)
        assertEquals("", preview)

        onKey(Key.NUM_2)
        assertEquals("2", display)
        assertEquals("", preview)

        onKey(Key.PLUS)
        assertEquals("2+", display)
        assertEquals("", preview)

        onKey(Key.NUM_2)
        assertEquals("2+2", display)
        assertEquals("4", preview)

        onKey(Key.EVAL)
        assertEquals("4", display)
        assertEquals("", preview)

        viewModel.clear()
        assertEquals("", display)
        assertEquals("", preview)
    }

    @Test
    fun factorial() {
        onKeys(Key.BRACKET_LEFT, Key.NUM_1, Key.PLUS, Key.NUM_4, Key.BRACKET_RIGHT)
        assertEquals("(1+4)", display)
        assertEquals("5", preview)

        onKey(Key.FACTORIAL)
        assertEquals("(1+4)!", display)
        assertEquals("120", preview)
    }

    @Test
    fun trig() {
        onKeys(Key.SIN, Key.NUM_5, Key.DOT, Key.NUM_0, Key.BRACKET_RIGHT)
        assertEquals("Sin(5.0)", display)
        assertEquals("-0.9589242746631385", preview)
    }

    @Test
    fun pow() {
        onKeys(Key.NUM_4, Key.POW, Key.NUM_3)
        assertEquals("4^3", display)
        assertEquals("64", preview)

        viewModel.clear()
        onKeys(Key.NUM_4, Key.POW, Key.BRACKET_LEFT, Key.NUM_1, Key.PLUS, Key.NUM_3, Key.BRACKET_RIGHT)
        assertEquals("4^(1+3)", display)
        assertEquals("256", preview)
    }

    @Test
    fun sqrt() {
        onKeys(Key.SQRT, Key.NUM_9, Key.BRACKET_RIGHT)
        assertEquals("Sqrt(9)", display)
        assertEquals("3", preview)
    }

    @Test
    fun constant() {
        onKeys(Key.E, Key.PLUS, Key.NUM_4, Key.DOT, Key.NUM_3)
        assertEquals("E+4.3", display)
        assertEquals("7.018281828459045", preview)

        viewModel.clear()
        onKeys(Key.PI, Key.PLUS, Key.NUM_4, Key.DOT, Key.NUM_3)
        assertEquals("Pi+4.3", display)
        assertEquals("7.441592653589793", preview)
    }

    @Test
    fun log() {
        onKeys(Key.LOG, Key.NUM_5, Key.DOT, Key.NUM_7, Key.BRACKET_RIGHT)
        assertEquals("Log10(5.7)", display)
        assertEquals("0.7558748556724915", preview)

        viewModel.clear()
        onKeys(Key.LN, Key.NUM_5, Key.DOT, Key.NUM_7, Key.BRACKET_RIGHT)
        assertEquals("Log(5.7)", display)
        assertEquals("1.7404661748405046", preview)
    }

    @Test
    fun precision() {
        val test = { input: String, expected: String ->
            viewModel.directInput(input)
            assertEquals(expected, preview)
        }

        test("1/3", "0.333333333333")
        test("1/3 + 1", "1.33333333333")
        test("(1/3 + 1) * 3", "4")

        test("2.12^9", "865.0132270093787")
        test("Sqrt(3)", "1.73205080757")
        test("Pi", "3.14159265359")
        test("Pi + 1", "4.14159265359")
        test("E", "2.71828182846")
        test("E + 1", "3.71828182846")

        test("Log10(2)", "0.301029995664")
        test("Log(2)", "0.693147180560")

        test("Sin(2)", "0.909297426826")
        test("Cos(2)", "-0.416146836547")
        test("Tan(2)", "-2.18503986326")
    }

    @Test
    fun arbitraryPrecisionHeld() {
        onKeys(Key.NUM_1, Key.DIVIDE, Key.NUM_3, Key.PLUS, Key.NUM_1, Key.EVAL)
        assertEquals("1.33333333333", display)
        assertEquals("", preview)

        onKeys(Key.TIMES, Key.NUM_3)
        assertEquals("1.33333333333*3", display)
        assertEquals("4", preview)
        onKeys(Key.EVAL)
        assertEquals("4", display)
        assertEquals("", preview)
    }

    @Test
    fun incrementalEval() {
        onKeys(Key.NUM_1, Key.DIVIDE, Key.NUM_3)
        assertEquals("0.333333333333", preview)
        onKey(Key.EVAL)
        assertEquals("0.333333333333", display)
        assertEquals("", preview)
        onKey(Key.TIMES)
        assertEquals("0.333333333333*", display)
        assertEquals("", preview)
        onKey(Key.NUM_3)
        assertEquals("0.333333333333*3", display)
        assertEquals("1", preview)
        onKey(Key.EVAL)
        assertEquals("1", display)
        assertEquals("", preview)
    }
}