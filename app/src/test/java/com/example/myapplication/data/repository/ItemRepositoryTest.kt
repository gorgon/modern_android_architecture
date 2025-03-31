package com.example.myapplication.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.InternalPlatformDsl.toArray
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.properties.Delegates.notNull
import kotlin.test.assertEquals

class ItemRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository = ItemRepositoryImpl()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `receive valid strings`() = runTest {
        val items = repository.addItem()
        advanceUntilIdle()

        assertTrue(items.toList().size == 1)
        assertEquals("My row number #1", items.first().first())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test addItem returns new item on success`() = runTest {
        val initialValues = mutableListOf<String>()
        val job = launch {
            repository.addItem().collect { newItems ->
                initialValues.addAll(newItems)
            }
        }
        advanceUntilIdle()
        job.cancel()
        // Validate that the list has grown by one item.
        assertEquals(1, initialValues.size)
    }

    @Test
    fun `test addItem simulates error at least once over multiple calls`() = runTest {
        var errorOccurred = false
        // Try multiple times to catch a simulated error.
        repeat(20) {
            try {
                repository.addItem()
            } catch (e: Exception) {
                errorOccurred = true
                assertEquals("Simulated network error", e.message)
                // Once an error is observed, we can break out.
                return@repeat
            }
        }
        if (!errorOccurred) {
            throw AssertionError("Simulated network error did not occur after multiple attempts")
        }
    }
}