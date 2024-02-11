package com.example.jetmessenger.data.api

import com.example.jetmessenger.BuildConfig
import com.example.jetmessenger.data.ReceivedMessage
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
        @Body message: ReceivedMessage
    ): Response<Unit>

}

val logger: HttpLoggingInterceptor = HttpLoggingInterceptor()
    .setLevel(HttpLoggingInterceptor.Level.BODY)

val httpClient: OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(logger)
    .build()