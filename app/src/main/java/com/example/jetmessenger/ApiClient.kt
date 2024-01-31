package com.example.jetmessenger

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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

val logger = HttpLoggingInterceptor()
    .setLevel(HttpLoggingInterceptor.Level.BODY)

val httpClient = OkHttpClient.Builder()
    .addInterceptor(logger)
    .build()

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://discord.com/api/")
    .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build()))
    .client(httpClient)
    .build()

@JsonClass(generateAdapter = true)
data class DiscordMessage(@field:Json(name = "content") val content: String)