package com.erdemserhat.data.mail

import com.erdemserhat.data.mail.MailServiceConstants.resetPasswordMailSubject
import com.erdemserhat.util.convertToString
import io.ktor.server.freemarker.FreeMarkerContent

/**
 * Sends a password reset email.
 * @param to The email address to send the email to.
 * @param code The password reset code.
 */
fun sendPasswordResetMail(
    to: String,
    code: String
) {
    // Prepare the content using FreeMarker template
    val page = FreeMarkerContent(
        template = "forgotpassword.ftl",
        model = mapOf("code" to code)
    )
    val contentAsString = convertToString(page)

    try {
        // Send the email
        sendEmail(
            to = to,
            subject = resetPasswordMailSubject,
            messageText = contentAsString
        )
    } catch (e: Exception) {
        // Handle exceptions
    }
}
