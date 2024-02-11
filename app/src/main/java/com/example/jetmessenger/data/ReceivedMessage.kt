package com.example.jetmessenger.data

data class ReceivedMessage(
    val content: String,
    val sender: String,
    val timestamp: Long
)
