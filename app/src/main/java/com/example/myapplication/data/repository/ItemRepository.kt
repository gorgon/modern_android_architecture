package com.example.myapplication.data.repository

interface ItemRepository {
    suspend fun addItem(): List<String>
}
