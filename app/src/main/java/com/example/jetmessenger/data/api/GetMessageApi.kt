package com.example.jetmessenger.data.api

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GetMessageApi {
    @GET("/channels/{channelId}/messages")
    suspend fun getMessages(
        @Path("channelId") channelId: String,
        @Query("limit") limit: Int
    ): Response<List<JsonObject>>

}