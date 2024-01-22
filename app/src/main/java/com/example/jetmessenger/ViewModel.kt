package com.example.jetmessenger
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jetmessenger.BuildConfig.WEBHOOK_URL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Inject
import javax.inject.Named

//Retorfitのインスタンスを作成
val retrofit = Retrofit.Builder()
    .baseUrl(WEBHOOK_URL)
    .build()

// Retrofitインターフェース

interface WebhookApi{
    @POST
    suspend fun sendMessage(@Body message: DiscordMessage)

// MainViewModel.kt

@HiltViewModel
class MainViewModel @Inject constructor(
    private val retrofit: Retrofit
) : ViewModel() {

    private val viewModelScope = CoroutineScope(Dispatchers.Main)

    //ここからWebhookApiの実装

    val webhookApi = retrofit.create(WebhookApi::class.java) //インターフェースを実装したものを作成
    val textState = MutableLiveData<String>()

    fun sendMessage() {
        viewModelScope.launch(Dispatchers.IO) {
            //textStateの値を取得し、DiscordMessageに変換して送信
            val message = DiscordMessage(textState.value!!)
            //Retrofitを通してAPIを叩く
            webhookApi.sendMessage(message)
    }
    }

    class WebhookApi @Inject constructor(@Named("webhookUrl") private val url: String) {

        fun sendMessage(message: DiscordMessage) {


        }

    }
}}

