package com.erdemserhat.service.configurations

import io.ktor.server.plugins.cors.routing.*
import io.ktor.http.*
import io.ktor.server.application.*

fun Application.configureCORS() {
    install(CORS) {
        // Specify allowed origins
        allowHost("harmonyhaventest.erdemserhat.com", schemes = listOf("https")) // Gerçek domainin
        allowHost("localhost:3000",schemes = listOf("http"))
        allowHost("harmonyhaven.erdemserhat.com",schemes = listOf("https"))

        // Or allow specific hosts
        // allowHost("localhost:3000", schemes = listOf("http"))

        // Specify allowed HTTP methods
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)

        // Specify allowed headers
        allowHeader("harmonyhavenapikey") // Belirli headerları izinli hale getir
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.ContentType)

        // If credentials like cookies or Authorization headers are sent
        allowCredentials = true
    }
}
