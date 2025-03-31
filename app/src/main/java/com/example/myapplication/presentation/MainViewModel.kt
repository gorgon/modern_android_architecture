package com.example.myapplication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.UiState
import com.example.myapplication.domain.model.UiState.Loading
import com.example.myapplication.domain.usecase.AddItemUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * The ViewModel processes intents and updates its state based on the result of executing the use case.
 * It now catches exceptions from the use case and updates the state to an error.
 */
class MainViewModel(private val addItemUseCase: AddItemUseCase) : ViewModel() {

    private val _state = MutableStateFlow<MainViewState>(MainViewState.Content(emptyList()))
    val state: StateFlow<MainViewState> = _state

    fun processIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.AddItem -> addItem()
        }
    }

    private fun addItem() {
        viewModelScope.launch {
            addItemUseCase.execute()
                .onStart { _state.value = MainViewState.Loading }
                .catch { e -> _state.value = MainViewState.Error(e.message ?: "Network error") }
                .collect { items -> _state.value = MainViewState.Content(items) }
        }
    }
}