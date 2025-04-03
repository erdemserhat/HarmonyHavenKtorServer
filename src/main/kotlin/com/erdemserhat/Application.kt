package com.erdemserhat

import com.erdemserhat.plugins.*
import com.erdemserhat.service.configurations.*
import com.erdemserhat.service.di.AuthenticationModule.tokenConfigSecurity
import com.erdemserhat.service.openai.configureOpenAiCredentials
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
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
    configureRouting()

}

