package com.example.jetmessenger.data

data class ReceivedMessage(
    val content: String,
    val author: User,
    val timestamp: Long
)