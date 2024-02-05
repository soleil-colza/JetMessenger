package com.example.jetmessenger.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class DiscordMessage(
    @field:Json(name = "content") val content: String
)