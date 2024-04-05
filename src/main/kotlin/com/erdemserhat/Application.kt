package com.erdemserhat

// Importing necessary modules and configurations
import com.erdemserhat.service.di.AuthenticationModule.tokenConfigSecurity
import com.erdemserhat.service.configurations.*
import com.erdemserhat.plugins.*
import io.ktor.server.application.*
import kotlinx.coroutines.*

// Main function responsible for starting the Ktor server
fun main(args: Array<String>) {
    // Launching the Ktor server using Netty engine
    io.ktor.server.netty.EngineMain.main(args)
}

// Main module for the Ktor application
@OptIn(DelicateCoroutinesApi::class)
fun Application.module() {
    // Configuring serialization for handling data formats
    configureSerialization()

    // Configuring templating for rendering HTML templates
    configureTemplating()

    // Configuring SMTP for sending emails
    configureSMTP()

    // Configuring OpenAI service for AI functionality
    configureOpenAIService()

    // Configuring FTP for file transfer
    configureFTP()

    // Configuring remote database connection
    configureRemoteDatabase()

    // Configuring Firebase for Firebase services
    configureFirebase()

    // Configuring token configuration for authentication
    configureTokenConfig()

    // Configuring security based on token configuration
    configureSecurity(tokenConfigSecurity)

    // Configuring routing for defining API endpoints
    configureRouting()
}
