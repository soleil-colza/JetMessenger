import com.example.jetmessenger.BuildConfig
import com.example.jetmessenger.BuildConfig.BASE_URL
import com.example.jetmessenger.data.ReceivedMessage
import com.example.jetmessenger.data.api.logger
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GetMessagesApi {
    @Headers("authorization: Bot ${BuildConfig.BOT_TOKEN}")
    @GET("channels/{channelId}/messages")
    suspend fun getMessages(
        @Path("channelId") channelId: String,
        @Query("limit") limit: Int = 10
    ): Array<ReceivedMessage>

}

val moshi: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val httpClient: OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(logger)
    .build()

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(httpClient)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

val api: GetMessagesApi = retrofit.create(GetMessagesApi::class.java)
