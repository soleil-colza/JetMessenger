package com.example.jetmessenger.data.repository

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.example.jetmessenger.BuildConfig
import com.example.jetmessenger.BuildConfig.CHANNEL_ID
import com.example.jetmessenger.data.DiscordMessage
import com.example.jetmessenger.data.api.SendMessageApi
import com.example.jetmessenger.data.api.httpClient
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException

class SendMessageRepositoryImpl(
    private val dispatcher: CoroutineDispatcher,
    sendMessageApi: SendMessageApi
) : SendMessageRepository {

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

    private val sendMessageApi: SendMessageApi = retrofit.newBuilder()
        .client(httpClient.newBuilder().addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .build()
            chain.proceed(request)
        }.build())
        .build()
        .create(SendMessageApi::class.java)

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun sendMessage(message: String) {
        withContext(dispatcher) {
            try {
                val discordMessage = DiscordMessage(content = message)
                sendMessageApi.sendMessage(CHANNEL_ID,  discordMessage)
            } catch (e: IOException) {
                Log.e("SendMessageRepository", "Networkエラー", e)
            } catch (e: HttpException) {
                Log.e("SendMessageRepository", "HTTPエラー")
            } catch (e: Exception) {
                Log.e("SendMessageRepository", "送信失敗", e)
            }
        }
    }
}
