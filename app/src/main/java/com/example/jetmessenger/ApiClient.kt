package com.example.jetmessenger

import retrofit2.http.Body
import retrofit2.http.POST

// メッセージを送信するインターフェース
interface discordWebhook {
    @POST
    suspend fun sendMessage(@Body message: discordMessage)
}

// 送信するデータクラス
data class discordMessage(
    val content: String
)