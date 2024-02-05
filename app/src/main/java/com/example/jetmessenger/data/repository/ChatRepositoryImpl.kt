package com.example.jetmessenger.data.repository

import android.util.Log
import com.example.jetmessenger.BuildConfig
import com.example.jetmessenger.BuildConfig.CHANNEL_ID
import com.example.jetmessenger.data.DiscordMessage
import com.example.jetmessenger.data.api.DiscordBot
import com.example.jetmessenger.data.api.httpClient
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ChatRepositoryImpl(
    private val dispatcher: CoroutineDispatcher
) : ChatRepository {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder().add(
                    KotlinJsonAdapterFactory()
                ).build()
            )
        )
        .client(httpClient)
        .build()

    private val discordBot = retrofit.create(DiscordBot::class.java)

    override suspend fun sendMessage(message: String) {
        withContext(dispatcher) {
            val discordMessage = DiscordMessage(
                content = message
            )

            try {
                discordBot.sendMessage(CHANNEL_ID, discordMessage)
            } catch (e: Exception) {
                Log.e("ChatRepository", "Error sending message", e)// エラーハンドリングの処理もっとちゃんとかく
            }
        }
    }
}