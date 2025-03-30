package com.example.myapplication.domain.usecase

import com.example.myapplication.data.repository.ItemRepository

class AddItemUseCase(private val repository: ItemRepository) {
    suspend fun execute(): List<String> {
        return repository.addItem()
    }
}