package com.example.jetmessenger.data.repository

import com.example.jetmessenger.BuildConfig
import com.example.jetmessenger.data.api.GetMessageApi
import com.google.gson.JsonObject
import retrofit2.Retrofit

class GetMessageRepositoryImpl {

    private val api = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .build()
        .create(GetMessageApi::class.java)

    suspend fun getMessages(channelId: String, limit: Int): List<JsonObject> {
        return api.getMessages(channelId, limit)
            .body() ?: emptyList()
    }

}