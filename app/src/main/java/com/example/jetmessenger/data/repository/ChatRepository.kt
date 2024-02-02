package com.example.jetmessenger.data.repository

import dev.kord.core.Kord
import dev.kord.core.entity.channel.TextChannel
import dev.kord.core.event.gateway.ReadyEvent

interface ChatRepository {
    suspend fun sendMessage(message: String) { //privateにできる？
    }
}
