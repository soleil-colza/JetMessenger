package com.example.jetmessenger.data.api

import com.example.jetmessenger.BuildConfig.WEBHOOK_URL
import com.example.jetmessenger.domain.DiscordMessage
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface DiscordWebhook {
    @POST("{channelId}/messages")
    suspend fun sendMessage(
        @Header("Authorization") token: DiscordMessage,
        @Path("channelId") channelId: String,
    ): Response<Unit>


    companion object {
        val instance: DiscordWebhook by lazy {
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            Retrofit.Builder()
                .baseUrl(WEBHOOK_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(httpClient)
                .build()
                .create(DiscordWebhook::class.java)
        }
    }
}

/*
suspend fun main() {
    val channelId = channelId
    val botToken = token

    val contentRequestBody = RequestBody.create(MediaType.parse("text/plain"), "こんにちは、世界！")

    val api = DiscordWebhook.instance
    val response = api.sendMessage(
        channelId
    )

    if (response.isSuccessful) {
        println("メッセージが正常に送信されました！")
    } else {
        println("メッセージの送信に失敗しました。レスポンスコード: ${response.code()}")
    }
}
*/
val logger: HttpLoggingInterceptor = HttpLoggingInterceptor()
    .setLevel(HttpLoggingInterceptor.Level.BODY)

val httpClient: OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(logger)
    .build()
