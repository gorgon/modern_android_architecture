package com.example.myapplication.domain.usecase

import com.example.myapplication.data.repository.ItemRepository
import kotlinx.coroutines.flow.Flow

class AddItemUseCase(private val repository: ItemRepository) {
    suspend fun execute(): Flow<List<String>> {
        return repository.addItem()
    }
}