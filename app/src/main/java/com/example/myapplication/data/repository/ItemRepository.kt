package com.example.myapplication.data.repository

import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    suspend fun addItem(): Flow<List<String>>
}
