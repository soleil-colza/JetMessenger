package com.example.jetmessenger.data

import com.squareup.moshi.Json

data class User(
    @Json(name = "id")
    val userId: String,
    val username: String,
    val discriminator: String,
)