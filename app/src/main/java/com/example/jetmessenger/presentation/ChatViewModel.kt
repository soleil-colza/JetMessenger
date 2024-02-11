package com.example.jetmessenger.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetmessenger.data.ReceivedMessage
import com.example.jetmessenger.data.repository.GetMessagesRepository
import com.example.jetmessenger.data.repository.SendMessageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(
    private val sendMessageRepository: SendMessageRepository,
    private val getMessageRepository: GetMessagesRepository
) : ViewModel() {

    private val _textState = MutableStateFlow("")
    val textState = _textState.asStateFlow()

    private val _messages = MutableStateFlow<List<ReceivedMessage>>(emptyList())
    val messages: StateFlow<List<ReceivedMessage>> = _messages

    fun fetchMessages() {
        viewModelScope.launch {
            _messages.value = getMessageRepository.getMessages()
        }
    }

    fun updateText(newText: String) {
        _textState.value = newText
    }

    fun sendMessage(newText: String) {
        viewModelScope.launch {
            sendMessageRepository.sendMessage(newText)
            updateText("")
            fetchMessages()
        }
    }

    init {
        viewModelScope.launch {
            val messages = getMessageRepository.getMessages()
            _messages.value = messages
        }
    }
}
