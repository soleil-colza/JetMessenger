package com.example.jetmessenger

import retrofit2.http.Body
import retrofit2.http.POST

// メッセージを送信するインターフェース
interface DiscordWebhook {
    @POST
    suspend fun sendMessage(@Body message: DiscordMessage)
}

// 送信するデータクラス
data class DiscordMessage(
    val content: String
)