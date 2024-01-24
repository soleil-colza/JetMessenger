package com.example.jetmessenger
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jetmessenger.BuildConfig.WEBHOOK_URL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit


//Retrofitインスタンスを作成。Base URLを指定しビルダーパターンでインスタンスを生成。
val retrofit = Retrofit.Builder()
    .baseUrl(WEBHOOK_URL)
    .build()

//ViewModelクラス(UIロジックを扱うクラス)を, ViewModel()を継承する形で定義。
class MainViewModel : ViewModel() {

    //RetrofitインスタンスからWebhookApiのインスタンスを生成。インターフェイスの実装が動的に生成される。
    val DiscordWebhook = retrofit.create(DiscordWebhook::class.java)

    //MutableLiveDataを生成。これはUIの状態を保持するために使用する。
    val textState = MutableLiveData<String>()

    //CoroutineScope(コルーチンのスコープを定義するもの)を生成。UIスレッドで実行するようにDispatchers.Mainを指定している。
    private val viewModelScope = CoroutineScope(Dispatchers.Main)

    //sendMessageメソッドで, viewModelScopeを使ってコルーチンを発行。IOスレッドでバックグラウンド処理を実行する。
    fun sendMessage() {
        viewModelScope.launch(Dispatchers.IO) {

            val rawText = textState.value
            var message: DiscordMessage? = null

            if (rawText != null && rawText.isEmpty()) {

                // textStateの値からDiscordMessageインスタンスを生成。
                //　Retrofitを通してAPIを叩き、WebhookApiに渡して送信。
                // !!でnull許容型を非null型に変換しているため, sendMessage()コール時にnullチェックを行う必要がある。
                message = DiscordMessage(rawText)
            }

            // ?.はSafe Call, letもnullでなければalsoの処理を実行する。
            textState.value
                ?.let { text -> DiscordMessage(text) }
                ?.also { message -> DiscordWebhook.sendMessage(message) }

        }
    }
}
