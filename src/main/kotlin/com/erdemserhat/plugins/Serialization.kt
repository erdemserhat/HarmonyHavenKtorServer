package com.erdemserhat.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*


/**
 * Configures content serialization for the application.
 * This function installs JSON content negotiation for serialization/deserialization.
 */
fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}