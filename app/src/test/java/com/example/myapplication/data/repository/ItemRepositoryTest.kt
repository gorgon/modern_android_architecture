package com.example.myapplication.data.repository

import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class ItemRepositoryTest {

    private val repository = ItemRepositoryImpl()

    @Test
    fun `receive valid strings`() = runTest {
        val items = repository.addItem()

        assertTrue(items.size == 1)
        assertTrue (items.get(0).equals("My row number #1"))
    }

    @Test
    fun `test addItem returns new item on success`() = runTest {
        // First call to addItem may return initial list
        val initialItems = try {
            repository.addItem()
        } catch (e: Exception) {
            // If a simulated error happens, we fail the test.
            throw AssertionError("Unexpected error during initial call: ${e.message}")
        }
        // Second call should add one more item if successful.
        val updatedItems = try {
            repository.addItem()
        } catch (e: Exception) {
            throw AssertionError("Unexpected error during second call: ${e.message}")
        }
        // Validate that the list has grown by one item.
        assertEquals(initialItems.size + 1, updatedItems.size)
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