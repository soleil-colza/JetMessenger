package com.example.jetmessenger.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class DiscordMessage(
    @field:Json(name = "content") val content: String
)