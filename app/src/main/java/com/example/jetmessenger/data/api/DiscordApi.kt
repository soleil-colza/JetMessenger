package com.example.jetmessenger.data.api

import com.example.jetmessenger.domain.DiscordMessage
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface DiscordBot {
    @POST("channels/{channelId}/messages")
    suspend fun sendMessage(
        @Header("authorization") token: String,
        @Path("channelId") channelId: String,
        @Body message: DiscordMessage
    ): Response<Unit>
}

val logger: HttpLoggingInterceptor = HttpLoggingInterceptor()
    .setLevel(HttpLoggingInterceptor.Level.BODY)

val httpClient: OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(logger)
    .build()
