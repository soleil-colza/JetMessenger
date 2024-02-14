package com.example.jetmessenger.data.repository

interface SendMessageRepository {
    suspend fun sendMessage(message: String)
}
