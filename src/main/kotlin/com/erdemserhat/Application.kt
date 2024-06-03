package com.erdemserhat

// Importing necessary modules and configurations
import com.erdemserhat.dto.requests.FcmNotification
import com.erdemserhat.dto.requests.SendNotificationDto
import com.erdemserhat.dto.requests.SendNotificationSpecific
import com.erdemserhat.dto.requests.toFcmMessage
import com.erdemserhat.models.Notification
import com.erdemserhat.service.di.AuthenticationModule.tokenConfigSecurity
import com.erdemserhat.service.configurations.*
import com.erdemserhat.plugins.*
import com.erdemserhat.service.di.DatabaseModule
import com.erdemserhat.service.openai.OpenAIPrompts
import com.erdemserhat.service.openai.OpenAIRequest
import com.google.firebase.messaging.FirebaseMessaging
import io.ktor.server.application.*
import kotlinx.coroutines.*
import java.sql.Timestamp

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

    val repo =DatabaseModule.notificationRepository

    val notification = Notification(
        id = 178,
        userId = 178,
        title = "Yeni bildirim",
        content = "Bu bir bildirim içeriğidir.",
        isRead = false,
        timeStamp = System.currentTimeMillis()/1000
    )






}
