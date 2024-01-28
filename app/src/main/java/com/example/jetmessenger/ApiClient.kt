package com.example.jetmessenger

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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
    )
}

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://discord.com/api/webhooks/1194601189988389007/RXOsVO_r7FfUoQf8KTRTRIol7SVx1zWWrvN9eT6vcdgLQYJoxmZ88PjW00jnhLpUh9Qk/")
    .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build()))
    .client(httpClient)
    .build()

@JsonClass(generateAdapter = true)
data class DiscordMessage(@field:Json(name = "content") val content: String)