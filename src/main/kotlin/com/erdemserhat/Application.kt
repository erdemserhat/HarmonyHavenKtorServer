package com.erdemserhat

// Importing necessary modules and configurations

import com.erdemserhat.plugins.*
import com.erdemserhat.service.configurations.*
import com.erdemserhat.service.di.AuthenticationModule.tokenConfigSecurity
import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.util.*
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.util.*
import io.ktor.client.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.sse.*
import io.ktor.sse.*
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.SerialName


@Serializable
data class Text(val text: String)


fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    //configureRateLimiting()
    //configureRateLimiting2()
    configureCORS()
    configureSerialization()
    configureTemplating()
    configureOpenAiCredentials()
    configureSMTP()
    configureFTP()
    configureRemoteDatabase()
    configureFirebase()
    configureTokenConfig()
    configureSecurity(tokenConfigSecurity)
    //configureApiKeySecurity()
    configureRouting()



}

