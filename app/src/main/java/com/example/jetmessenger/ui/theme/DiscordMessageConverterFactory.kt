package com.example.jetmessenger.ui.theme
import com.example.jetmessenger.DiscordMessage
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class DiscordMessageConverterFactory(contentType: String) : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        return null
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        if (type == DiscordMessage::class.java) {
            return DiscordMessageConverter
        }
        return null
    }

    private object DiscordMessageConverter : Converter<DiscordMessage, RequestBody> {
        private val MEDIA_TYPE = MediaType.get("application/json; charset=UTF-8")

        override fun convert(value: DiscordMessage): RequestBody {
            return RequestBody.create(MEDIA_TYPE, value.toJson())
        }
    }
}
