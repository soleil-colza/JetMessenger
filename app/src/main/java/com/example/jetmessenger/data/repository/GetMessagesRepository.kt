package com.example.jetmessenger.data.repository

import android.os.Message

interface GetMessagesRepository {
    suspend fun getMessages(): List<Message>
}