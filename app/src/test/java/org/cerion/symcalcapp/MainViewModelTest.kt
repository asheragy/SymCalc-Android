package org.cerion.symcalcapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule


internal class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @org.junit.Before
    fun beforeEach() {
        viewModel = MainViewModel()
    }

    @Test
    fun basic() {
        assertEquals("",viewModel.display.value)
        assertEquals("", viewModel.preview.value)

        viewModel.apply {
            onKey(Key.NUM_2)
            assertEquals("2", viewModel.display.value)
            assertEquals("", viewModel.preview.value)

            onKey(Key.PLUS)
            assertEquals("2+", viewModel.display.value)
            assertEquals("", viewModel.preview.value)

            onKey(Key.NUM_2)
            assertEquals("2+2", viewModel.display.value)
            assertEquals("4", viewModel.preview.value)

            onKey(Key.EVAL)
            assertEquals("4", viewModel.display.value)
            assertEquals("", viewModel.preview.value)
        }
    }
}