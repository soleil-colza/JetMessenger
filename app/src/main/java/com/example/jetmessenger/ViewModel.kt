package com.example.jetmessenger

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetmessenger.BuildConfig.WEBHOOK_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit

val retrofit = Retrofit.Builder()
    .baseUrl(WEBHOOK_URL)
    .build()

class MainViewModel : ViewModel() {

    val textState = MutableLiveData("")

    private val discordWebhook = retrofit.create(DiscordWebhook::class.java)
    fun sendMessage() {
        viewModelScope.launch(Dispatchers.IO) {
            textState.value?.let { text ->
                // textStateの値がnullでない場合、DiscordMessageを生成してWebhookに送信
                discordWebhook.sendMessage(DiscordMessage(text))
            }
        }
    }
}