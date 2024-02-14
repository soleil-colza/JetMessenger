package com.example.jetmessenger.data.api

import com.example.jetmessenger.BuildConfig
import com.example.jetmessenger.data.DiscordMessage
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface SendMessageApi {
    @Headers("authorization: Bot ${BuildConfig.BOT_TOKEN}")
    @POST("channels/{channelId}/messages")
    suspend fun sendMessage(
        @Path("channelId") channelId: String,
        @Body message: DiscordMessage
    ): Response<Unit>

}

val sendMessageApi: SendMessageApi = retrofit.create(SendMessageApi::class.java)

val logger: HttpLoggingInterceptor = HttpLoggingInterceptor()
    .setLevel(HttpLoggingInterceptor.Level.BODY)

val httpClient: OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(logger)
    .build()