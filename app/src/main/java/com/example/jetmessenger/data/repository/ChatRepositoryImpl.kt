package com.example.jetmessenger.data.repository

import com.example.jetmessenger.BuildConfig
import com.example.jetmessenger.data.api.DiscordMessage
import com.example.jetmessenger.data.api.DiscordWebhook
import com.example.jetmessenger.data.api.httpClient
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ChatRepositoryImpl : ChatRepository {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://discord.com/api/")
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder().add(
                    KotlinJsonAdapterFactory()
                ).build()))
        .client(httpClient)
        .build()

    private val discordWebhook = retrofit.create(DiscordWebhook::class.java)


    override suspend fun sendMessage(message: String) {
        discordWebhook.sendMessage(
            DiscordMessage(content = message),
            webhookId = BuildConfig.WEBHOOK_ID,
            webhookToken = BuildConfig.WEBHOOK_TOKEN
        )
    }
}
