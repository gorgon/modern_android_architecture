package com.example.myapplication.di

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myapplication.presentation.MainViewModel
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get


class DependencyInjectionTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        // Provide your test modules; if you’re overriding production modules, include them here.
        modules(appModule)
    }

    @Test
    fun `should inject MainViewModel successfully`() {
        // Retrieve MainViewModel from Koin
        val viewModel: MainViewModel = get()
        // Assert that the ViewModel is injected (i.e., not null)
        assertNotNull(viewModel)
    }
}
