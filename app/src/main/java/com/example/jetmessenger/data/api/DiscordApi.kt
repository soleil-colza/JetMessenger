package com.example.jetmessenger.data.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface DiscordWebhook {
    @POST("webhooks/{webhookId}/{webhookToken}")
    suspend fun sendMessage(
        @Body message: DiscordMessage,
        @Path("webhookId") webhookId: String,
        @Path("webhookToken") webhookToken: String,
    ): Response<Unit>
}

val logger: HttpLoggingInterceptor = HttpLoggingInterceptor()
    .setLevel(HttpLoggingInterceptor.Level.BODY)

val httpClient: OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(logger)
    .build()


@JsonClass(generateAdapter = true)
data class DiscordMessage(@field:Json(name = "content") val content: String)