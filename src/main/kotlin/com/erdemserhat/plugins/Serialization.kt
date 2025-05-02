package com.erdemserhat.plugins

import com.erdemserhat.data.database.nosql.ObjectIdSerializer
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import org.bson.types.ObjectId
import org.slf4j.event.Level


/**
 * Configures content serialization for the application.
 * This function installs JSON content negotiation for serialization/deserialization.
 */
fun Application.configureSerialization() {
    println("Serialization Configured")
    install(ContentNegotiation) {
        json(Json {
            serializersModule = SerializersModule {
                contextual(ObjectId::class, ObjectIdSerializer) // Özel serializer tanımlaması
            }
        })
    }

    install(CallLogging) {
        level = Level.DEBUG // Set logging level to DEBUG to see detailed logs
        format { call ->
            // Custom log message format
            "HTTP ${call.request.httpMethod} ${call.request.uri} -> ${call.response.status()}"
        }
    }


}