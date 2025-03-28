package com.erdemserhat.data.mail

import com.erdemserhat.data.mail.MailServiceConstants.welcomeMailSubject
import com.erdemserhat.util.convertToString
import io.ktor.server.freemarker.FreeMarkerContent

/**
 * Sends a welcome email.
 * @param to The email address to send the email to.
 * @param name The name of the recipient.
 */
fun sendWelcomeMail(
    to: String,
    name: String
) {
    // Prepare the content using FreeMarker template
    val page = FreeMarkerContent(
        template = "welcome.ftl",
        model = mapOf("name" to name)
    )
    val contentAsString = convertToString(page)

    try {
        // Send the email
        sendEmail(
            to = to,
            subject = welcomeMailSubject,
            messageText = contentAsString
        )
    } catch (e: Exception) {
        // Handle exceptions
    }
}
