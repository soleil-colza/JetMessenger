package com.example.jetmessenger

import com.google.gson.Gson
import retrofit2.http.Body
import retrofit2.http.POST

interface DiscordWebhook {
    @POST("sendMessage")
    suspend fun sendMessage(@Body discordMessage: DiscordMessage)
}

data class DiscordMessage(val text: String) {
    fun toJson(): String = GsonSingleton.gson.toJson(this)
}

object GsonSingleton {
    val gson: Gson = Gson()
}