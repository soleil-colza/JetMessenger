package com.example.jetmessenger.data

data class DummyMessage(
    val id: Int,
    val content: String = "This is a dummy message",
    val author: String = "Dummy Author",
    val timestamp: String = "12:34 PM"
)
