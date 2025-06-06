package com.erdemserhat.service.configurations

import io.ktor.server.plugins.cors.routing.*
import io.ktor.http.*
import io.ktor.server.application.*

fun Application.configureCORS() {
    install(CORS) {
        // Specify allowed origins
        // Or allow specific hosts

        allowHost("www.harmonyhavenapp.com", schemes = listOf("http", "https"))
        allowHost("harmonyhavenapp.com", schemes = listOf("http", "https"))
        allowHost("localhost:3000", schemes = listOf("http", "https"))


        // Specify allowed HTTP methods
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)



        // Specify allowed headers
        allowHeader("harmonyhavenapikey") // Belirli headerları izinli hale getir
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.ContentType)

        // If credentials like cookies or Authorization headers are sent
        allowCredentials = true
    }
}
