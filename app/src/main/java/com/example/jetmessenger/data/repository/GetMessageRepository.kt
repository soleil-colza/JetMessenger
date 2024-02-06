package com.example.jetmessenger.data.repository

import com.example.jetmessenger.data.api.GetMessageApi
import com.google.gson.JsonObject
import retrofit2.Retrofit

interface GetMessageRepository {
    private val api: GetMessageApi
        get() = Retrofit.Builder()
            .baseUrl("https://discord.com/api/")
            .build()
            .create(GetMessageApi::class.java)

    suspend fun getMessages(channelId: String, limit: Int): List<JsonObject> {
        return api.getMessages(channelId, limit)
            .body() ?: emptyList()
    }

}