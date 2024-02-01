package com.example.jetmessenger.data.repository

interface ChatRepository {
    suspend fun sendMessage(message: String) { //privateにできる？
    }
}
