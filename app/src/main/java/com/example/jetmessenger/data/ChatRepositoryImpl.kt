package com.example.jetmessenger.data

import com.example.jetmessenger.BuildConfig
import com.example.jetmessenger.BuildConfig.WEBHOOK_URL
import com.example.jetmessenger.data.api.DiscordWebhook
import com.example.jetmessenger.data.api.httpClient
import com.example.jetmessenger.data.repository.ChatRepository
import com.example.jetmessenger.domain.DiscordMessage
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ChatRepositoryImpl(
    private val channelId: String = BuildConfig.channelId,
    private val token: String = BuildConfig.token
) : ChatRepository {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(WEBHOOK_URL)
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
            channelId = BuildConfig.channelId,
        )
    }
}