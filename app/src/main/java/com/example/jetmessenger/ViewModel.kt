package com.example.jetmessenger

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

val logger = HttpLoggingInterceptor()
    .setLevel(HttpLoggingInterceptor.Level.BODY)

val httpClient = OkHttpClient.Builder()
    .addInterceptor(logger)
    .build()

class MainViewModel : ViewModel() {

    private val _textState = MutableStateFlow("")
    val textState = _textState.asStateFlow()

    private val discordWebhook = retrofit.create(DiscordWebhook::class.java)
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun sendMessage(newText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _textState.value = newText
            discordWebhook.sendMessage(
                DiscordMessage(content = newText),
                webhookId = BuildConfig.WEBHOOK_ID,
                webhookToken = BuildConfig.WEBHOOK_TOKEN
            )
        }
    }
}
