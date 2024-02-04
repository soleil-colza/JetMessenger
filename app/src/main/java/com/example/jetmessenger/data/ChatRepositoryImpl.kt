package com.example.jetmessenger.data

import com.example.jetmessenger.BuildConfig.baseUrl
import com.example.jetmessenger.BuildConfig.channelId
import com.example.jetmessenger.data.api.DiscordBot
import com.example.jetmessenger.data.api.httpClient
import com.example.jetmessenger.data.repository.ChatRepository
import com.example.jetmessenger.domain.DiscordMessage
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ChatRepositoryImpl : ChatRepository {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder().add(
                    KotlinJsonAdapterFactory()
                ).build()))
        .client(httpClient)
        .build()

    private val discordBot = retrofit.create(DiscordBot::class.java)

    override suspend fun sendMessage(message: String) {

        val discordMessage = DiscordMessage(
            content = message
        )

        discordBot.sendMessage(
            channelId = channelId,
            message = discordMessage
        )

    }
}