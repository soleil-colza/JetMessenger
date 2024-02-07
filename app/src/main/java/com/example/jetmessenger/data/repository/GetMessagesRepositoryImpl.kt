package com.example.jetmessenger.data.repository

import GetMessagesApi
import android.os.Message
import com.example.jetmessenger.BuildConfig.CHANNEL_ID
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetMessagesRepositoryImpl(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val getMessagesApi: GetMessagesApi
) : GetMessagesRepository {

    override suspend fun getMessages(): List<Message> {
        return try {
            val channelId = CHANNEL_ID
            withContext(coroutineDispatcher) {
                getMessagesApi.getMessages(channelId)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
