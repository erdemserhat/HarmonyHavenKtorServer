package com.erdemserhat

// Importing necessary modules and configurations

import com.erdemserhat.data.database.comment.CommentDaoImpl
import com.erdemserhat.data.database.liked_comment.LikedCommentImpl
import com.erdemserhat.service.di.AuthenticationModule.tokenConfigSecurity
import com.erdemserhat.service.configurations.*
import com.erdemserhat.plugins.*
import com.erdemserhat.service.configurations.rate_limiting.configureRateLimiting
import com.erdemserhat.service.configurations.rate_limiting.configureRateLimiting2
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import kotlinx.coroutines.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.server.netty.*
import io.ktor.util.*
import kotlinx.serialization.Serializable
import javax.xml.stream.events.Comment


fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    //configureRateLimiting()
    //configureRateLimiting2()
    configureCORS()
    configureSerialization()
    configureTemplating()
    configureSMTP()
    configureOpenAIService()
    configureFTP()
    configureRemoteDatabase()
    configureFirebase()
    configureTokenConfig()
    configureSecurity(tokenConfigSecurity)
    //configureApiKeySecurity()
    configureRouting()

    CoroutineScope(Dispatchers.IO).launch {
        //  configureNotificationScheduler()


    }




}


@OptIn(InternalAPI::class)
suspend fun makeEncryptionRequest(
    encryptionToFarawayServerModel: EncryptionToFarawayServerModel = EncryptionToFarawayServerModel()
): String {
    val client = HttpClient(CIO)
    try {
        val response: HttpResponse = client.post(encryptionToFarawayServerModel.url) {
            contentType(ContentType.Application.Json)
            body = Gson().toJson(encryptionToFarawayServerModel.encryptionData)
        }

        val responseBody: String = response.body()
        val map: Map<String, String> = jsonToMap(responseBody)

        return (map["encrypted_data"])!!
    } finally {
        client.close()
    }
}


@Serializable
data class EncryptionToFarawayServerModel(
    val url: String = "http://localhost:8000/encode",
    var encryptionData: EncryptionDataDto = EncryptionDataDto()

)

@Serializable
data class EncryptionDataDto(
    val apiKey: String = "de451275-4175-4fc0-8dbe-14b35480f522",
    val userUUID: String = "63dadff8-a188-4604-84a8-577bb2192781",
    var sensitiveData: String = "ExampleSensietiveData"

)


fun jsonToMap(json: String): Map<String, String> {
    val gson = Gson()
    val type = object : TypeToken<Map<String, Any>>() {}.type
    return gson.fromJson(json, type)
}
