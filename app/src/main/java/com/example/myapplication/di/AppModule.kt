package com.example.myapplication.di

import com.example.myapplication.data.repository.ItemRepository
import com.example.myapplication.data.repository.ItemRepositoryImpl
import com.example.myapplication.domain.usecase.AddItemUseCase
import com.example.myapplication.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<ItemRepository> { ItemRepositoryImpl() }
    single { AddItemUseCase(get()) }
    viewModel { MainViewModel(get()) }
}