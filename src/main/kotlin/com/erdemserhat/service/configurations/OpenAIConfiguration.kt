package com.erdemserhat.service.configurations

import com.aallam.openai.api.http.Timeout
import com.aallam.openai.client.OpenAI
import com.erdemserhat.service.di.OpenAIModule
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.time.Duration.Companion.seconds

fun Application.configureOpenAIService() {
    OpenAIModule.openAI = OpenAI(
        token = environment.config.property("openai.key").getString(),
        timeout = Timeout(socket = 60.seconds)
    )
}