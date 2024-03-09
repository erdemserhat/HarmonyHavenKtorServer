package com.erdemserhat.domain.email

import com.erdemserhat.domain.email.MailServiceConstants.resetPasswordMailSubject
import io.ktor.server.freemarker.*
import com.erdemserhat.util.convertToString

fun sendPasswordResetMail(
    to: String,
    code: String,

) {
    val page = FreeMarkerContent(
        template = "forgotpassword.ftl",
        model = mapOf("code" to code)
    )

    val contentAsString = convertToString(page)
    try {
        sendEmail(
            to = to,
            subject = resetPasswordMailSubject,
            messageText = contentAsString
        )

    } catch (e: Exception) {
        e.printStackTrace().toString()
    }

}