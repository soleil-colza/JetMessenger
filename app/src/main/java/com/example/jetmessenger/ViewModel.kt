package com.example.jetmessenger

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetmessenger.BuildConfig.WEBHOOK_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit

//Retrofitインスタンスを作成。Base URLを指定しビルダーパターンでインスタンスを生成。
val retrofit = Retrofit.Builder()
    .baseUrl(WEBHOOK_URL)
    .build()

//ViewModelクラス(UIロジックを扱うクラス)を, ViewModel()を継承する形で定義。
class MainViewModel : ViewModel() {

    public val textState = MutableLiveData("")

    //RetrofitインスタンスからWebhookApiのインスタンスを生成。インターフェイスの実装が動的に生成される。
    val DiscordWebhook = retrofit.create(DiscordWebhook::class.java)

    //sendMessageメソッドで, viewModelScopeを使ってコルーチンを発行。IOスレッドでバックグラウンド処理を実行する。
    fun sendMessage() {
        viewModelScope.launch(Dispatchers.IO) {
            val rawText = textState.value
            var message: DiscordMessage? = null

            textState.value?.let { text ->
                // textStateの値がnullでない場合、DiscordMessageを生成してWebhookに送信
                DiscordWebhook.sendMessage(DiscordMessage(text))
            }
        }
    }
}