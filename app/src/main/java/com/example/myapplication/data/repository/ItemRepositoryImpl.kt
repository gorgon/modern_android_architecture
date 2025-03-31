package com.example.myapplication.data.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlin.random.Random

class ItemRepositoryImpl : ItemRepository {
    private val items = mutableListOf<String>()

    override suspend fun addItem(): Flow<List<String>> = flow {
        // Simulate a long-running task
        delay(3000)
        // Randomly simulate an error (e.g., network failure)
        if (Random.nextFloat() < 0.1f) {
            throw Exception("Simulated network error")
        }
        items.add("My row number #${items.size + 1}")
        emit(items.toList())
    }
}