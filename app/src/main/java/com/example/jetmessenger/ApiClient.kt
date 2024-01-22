package com.example.jetmessenger
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST


// Retrofitでクライアントを作成
val client = Retrofit.Builder()
    .baseUrl("WEBHOOK_URL" + "/")
    .client(OkHttpClient())
    .build()
    .create(DiscordWebhook::class.java)

// メッセージを送信するインターフェース
interface DiscordWebhook {
    @POST
    suspend fun sendMessage(@Body message: DiscordMessage)
}

// 送信するデータクラス
data class DiscordMessage(
    val content: String
)