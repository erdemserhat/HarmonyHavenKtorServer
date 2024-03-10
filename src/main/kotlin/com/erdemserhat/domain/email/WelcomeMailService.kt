package com.erdemserhat.domain.email

import com.erdemserhat.domain.email.MailServiceConstants.welcomeMailSubject
import io.ktor.server.freemarker.*
import com.erdemserhat.util.convertToString

fun sendWelcomeMail(
    to: String,
    name: String
) {
    val page = FreeMarkerContent(
        template = "welcome.ftl",
        model = mapOf("name" to name)
    )

    val contentAsString = convertToString(page)
    try {
        sendEmail(
            to = to,
            subject = welcomeMailSubject,
            messageText = contentAsString
        )

    } catch (e: Exception) {
        e.printStackTrace().toString()
    }

}