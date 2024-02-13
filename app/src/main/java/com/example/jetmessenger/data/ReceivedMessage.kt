package com.example.jetmessenger.data

import com.squareup.moshi.Json

data class ReceivedMessage(
    val id: String,
    @Json(name = "channel_id")
    val channelId: String,
    val author: String,
    val content: String,
    val timestamp: String,
)

