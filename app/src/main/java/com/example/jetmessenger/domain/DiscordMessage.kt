package com.example.jetmessenger.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DiscordMessage(
    @field:Json(name = "content") val content: String
)