package com.example.jetmessenger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetmessenger.BuildConfig.WEBHOOK_URL
import com.example.jetmessenger.ui.theme.DiscordMessageConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(WEBHOOK_URL)
    .addConverterFactory(DiscordMessageConverterFactory())
    .build()
class MainViewModel : ViewModel() {

    private val _textState = MutableStateFlow("")
    val textState = _textState.asStateFlow()

    private val discordWebhook = retrofit.create(DiscordWebhook::class.java)
    fun sendMessage(newText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _textState.value = newText
            discordWebhook.sendMessage(DiscordMessage(newText))
        }
    }
}