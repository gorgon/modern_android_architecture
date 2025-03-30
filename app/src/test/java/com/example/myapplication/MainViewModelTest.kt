package com.example.myapplication

import com.example.myapplication.domain.usecase.AddItemUseCase
import com.example.myapplication.presentation.MainIntent
import com.example.myapplication.presentation.MainViewModel
import com.example.myapplication.presentation.MainViewState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

//    @get:Rule
//    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var addItemUseCase: AddItemUseCase
    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        addItemUseCase = mockk()
        viewModel = MainViewModel(addItemUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when addItemUseCase returns items then state becomes Content`() = runTest {
        // Given
        val items = listOf("Item 1")
        coEvery { addItemUseCase.execute() } returns items

        // When
        viewModel.processIntent(MainIntent.AddItem)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertTrue(viewModel.state.value is MainViewState.Content)
    }

    @Test
    fun `when addItemUseCase throws exception then state becomes Error`() = runTest {
        // Given
        coEvery { addItemUseCase.execute() } throws Exception("Network error")

        // When
        viewModel.processIntent(MainIntent.AddItem)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertTrue(viewModel.state.value is MainViewState.Error)
    }
}
