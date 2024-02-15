package com.example.jetmessenger.data

data class ChatUiState(
    val inputText: String = "",
    val messages: Array<ReceivedMessage> = emptyArray(),
    val isLoading : Boolean = true
)