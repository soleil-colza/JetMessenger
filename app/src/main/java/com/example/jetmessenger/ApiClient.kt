package com.example.jetmessenger

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.http.Body
import retrofit2.http.POST

interface DiscordWebhook {
    @POST("sendMessage")
    suspend fun sendMessage(@Body discordMessage: DiscordMessage)
}

@JsonClass(generateAdapter = true)
data class DiscordMessage(@field:Json(name = "id") val id: String)