package com.example.jetmessenger.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetmessenger.data.ChatUiState
import com.example.jetmessenger.data.repository.GetMessagesRepository
import com.example.jetmessenger.data.repository.SendMessageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(
    private val sendMessageRepository: SendMessageRepository,
    private val getMessagesRepository: GetMessagesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    fun fetchMessages() {
        viewModelScope.launch {
            val messages = getMessagesRepository.getMessages()
            _uiState.value = _uiState.value.copy(messages = messages)
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
