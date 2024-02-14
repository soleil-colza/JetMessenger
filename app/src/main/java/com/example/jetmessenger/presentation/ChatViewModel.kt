package com.example.jetmessenger.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetmessenger.data.ChatUiState
import com.example.jetmessenger.data.repository.GetMessagesRepository
import com.example.jetmessenger.data.repository.SendMessageRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(
    private val sendMessageRepository: SendMessageRepository,
    private val getMessagesRepository: GetMessagesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState(isLoading = false))
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    private fun fetchMessages() {

        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch {
            delay(3000)
            val messages = getMessagesRepository.getMessages()
            _uiState.value = _uiState.value.copy(messages = messages, isLoading = false)
        }
    }

    fun updateText(newText: String) {
        _uiState.value = _uiState.value.copy(inputText = newText)
    }

    fun sendMessage(newText: String) {
        viewModelScope.launch {
            sendMessageRepository.sendMessage(newText)
            updateText("")
            fetchMessages()
        }
    }

    init {
        fetchMessages()
    }
}