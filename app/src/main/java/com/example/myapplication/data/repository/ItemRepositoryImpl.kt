package com.example.myapplication.data.repository

import kotlinx.coroutines.delay
import kotlin.random.Random

class ItemRepositoryImpl : ItemRepository {
    private val items = mutableListOf<String>()

    override suspend fun addItem(): List<String> {
        // Simulate a long-running task
        delay(3000)
        // Randomly simulate an error (e.g., network failure)
        if (Random.nextFloat() < 0.3f) {
            throw Exception("Simulated network error")
        }
        items.add("My row number #${items.size + 1}")
        return items.toList()
    }
}