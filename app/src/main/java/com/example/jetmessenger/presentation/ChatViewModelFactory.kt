package com.example.jetmessenger.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetmessenger.data.repository.GetMessagesRepository
import com.example.jetmessenger.data.repository.SendMessageRepository

class ChatViewModelFactory(
    private val sendMessageRepository: SendMessageRepository,
    private val getMessagesRepository: GetMessagesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            return ChatViewModel(sendMessageRepository, getMessagesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
