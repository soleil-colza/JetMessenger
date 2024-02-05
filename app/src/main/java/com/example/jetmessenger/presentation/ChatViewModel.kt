package com.example.jetmessenger.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetmessenger.data.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(
    private val repository: ChatRepository
) : ViewModel() {

    private val _textState = MutableStateFlow("")
    val textState = _textState.asStateFlow()

    fun updateText(newText: String) {
        viewModelScope.launch {
            _textState.value = newText
        }
    }

    fun sendMessage(newText: String) {
        viewModelScope.launch {
            repository.sendMessage(newText)
            updateText("")
        }
    }
}