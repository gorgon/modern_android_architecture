package com.example.myapplication.presentation

sealed class MainViewState {
    object Loading : MainViewState()
    data class Content(val items: List<String>) : MainViewState()
    data class Error(val error: String) : MainViewState()
}