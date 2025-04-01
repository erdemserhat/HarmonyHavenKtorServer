package com.erdemserhat.service.configurations

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.typesafe.config.ConfigFactory
import io.ktor.server.application.*
import java.security.GeneralSecurityException
import java.util.*


val config = ConfigFactory.load()
val jsonFactory = JacksonFactory.getDefaultInstance()
val httpTransport = GoogleNetHttpTransport.newTrustedTransport()
val clientId = config.getString("harmonyhaven.google.googleClientId")

val verifier = GoogleIdTokenVerifier.Builder(httpTransport, jsonFactory)
    .setAudience(Collections.singletonList(clientId))
    .build()

fun ApplicationCall.validateGoogleIdToken(idTokenString: String): GoogleIdToken? {
    return try {
        val idToken = verifier.verify(idTokenString)
        if (idToken != null) {
            // Token is valid
            idToken
        } else {
            // Invalid token
            null
        }
    } catch (e: GeneralSecurityException) {
        // Handle exception
        null
    }
}
