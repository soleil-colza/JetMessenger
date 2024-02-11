
import com.example.jetmessenger.BuildConfig
import com.example.jetmessenger.BuildConfig.BASE_URL
import com.example.jetmessenger.data.ReceivedMessage
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface GetMessagesApi {
    @Headers("authorization: Bot ${BuildConfig.BOT_TOKEN}")
    @GET("/channels/{channelId}/messages")
    suspend fun getMessages(
        @Path("channelId") channelId: String
    ): List<ReceivedMessage>

}

val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

val api = retrofit.create(GetMessagesApi::class.java)