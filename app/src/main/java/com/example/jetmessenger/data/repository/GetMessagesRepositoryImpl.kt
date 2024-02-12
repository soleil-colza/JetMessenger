package com.example.jetmessenger.data.repository

import GetMessagesApi
import android.util.Log
import com.example.jetmessenger.BuildConfig.CHANNEL_ID
import com.example.jetmessenger.data.ReceivedMessage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetMessagesRepositoryImpl(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val getMessagesApi: GetMessagesApi
) : GetMessagesRepository {

    override suspend fun getMessages(): List<ReceivedMessage> {
        return try {
            val channelId = CHANNEL_ID
            val messages = withContext(coroutineDispatcher) {
                getMessagesApi.getMessages(channelId)
            }
            Log.d("GetMessagesRepository", "Messages: $messages")
            messages
        } catch (e: Exception) {
            Log.e("GetMessagesRepository", "Error fetching messages", e)
            emptyList()
        }
    }
}
