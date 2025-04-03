package com.erdemserhat.service.openai

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.sse.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

object OpenAIClient {
    private val client = HttpClient(CIO) {
        install(SSE) {
            showCommentEvents()
            showRetryEvents()
        }

        install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
            json(Json { prettyPrint = true }) // JSON için serileştirme

        }


    }

    suspend fun makeStreamChatRequest(
         prompt:String,
         username: String,
         request: Any
    ): Flow<String> = flow {
        try {

            client.sse(
                scheme = OpenAICredentials.SCHEME,
                host = OpenAICredentials.HOST,
                port = OpenAICredentials.PORT,
                path = OpenAICredentials.PATH,
                request = {
                    method = HttpMethod.Post
                    setBody(request)
                    contentType(ContentType.Application.Json)
                    header("Authorization", "Bearer ${OpenAICredentials.API_KEY}")
                }


            ) {

                incoming.collect { event ->
                    val data = event.data!!
                  //  println(data)

                    if (data == "[DONE]") {
                        //emit("END")
                        return@collect
                    }

                    try {
                        val responseObj = Json {
                            ignoreUnknownKeys = true
                        }.decodeFromString<OpenAIStreamingResponse>(data)

                        emit(responseObj.choices.firstOrNull()!!.delta.content!!)
                    } catch (e: Exception) {
                        //println("JSON parse hatası: ${e.message}")
                    }
                }





            }


        } catch (e: Exception) {
            println("---->${e.message}")
        }


    }


}

@Serializable
data class OpenAIRequest(
    val model: String,
    val messages: List<Message>,
    val temperature: Double,
    val stream: Boolean
)

@Serializable
data class Message(
    val role: String,
    val content: String
)

@Serializable
data class OpenAIStreamingResponse(
    val id: String,
    val `object`: String,
    val created: Long,
    val model: String,
    val service_tier: String,
    val system_fingerprint: String? = null,
    val choices: List<Choice>
)

@Serializable
data class Choice(
    val index: Int,
    val delta: Delta,
    val logprobs: String? = null,
    val finish_reason: String? = null
)

@Serializable
data class Delta(
    val content: String? = null,

    // Fazladan gelebilecek anahtarları hata vermeden yok say
    @SerialName("role") val role: String? = null
)

