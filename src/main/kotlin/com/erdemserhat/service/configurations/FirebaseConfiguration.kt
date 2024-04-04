package com.erdemserhat.service.configurations

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import io.ktor.server.application.*

/**
 * Configures Firebase for the application.
 * This function initializes Firebase with the provided service account credentials.
 */
fun Application.configureFirebase() {
    // Load the service account JSON file from resources
    val serviceAccountStream = this::class.java.classLoader.getResourceAsStream("firebase_service_key.json")

    // Set up Firebase options with the loaded credentials
    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
        .build()

    // Initialize Firebase with the configured options
    FirebaseApp.initializeApp(options)
}
