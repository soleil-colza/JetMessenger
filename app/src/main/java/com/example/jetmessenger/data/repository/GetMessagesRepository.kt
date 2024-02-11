package com.example.jetmessenger.data.repository

import com.example.jetmessenger.data.ReceivedMessage

interface GetMessagesRepository {
    suspend fun getMessages(): List<ReceivedMessage>
}