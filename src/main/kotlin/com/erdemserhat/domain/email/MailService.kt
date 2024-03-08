package com.erdemserhat.domain.email

import com.erdemserhat.domain.email.EmailConfig.SESSION
import com.erdemserhat.domain.email.EmailConfig.SMTP_USERNAME
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart


fun sendEmail(to: String, subject: String, messageText: String) {

    val message: Message = MimeMessage(SESSION)

    message.setFrom(InternetAddress(SMTP_USERNAME))
    message.setRecipients(
        Message.RecipientType.TO, InternetAddress.parse(to)
    )
    message.subject = subject

    val mimeBodyPart = MimeBodyPart()
    mimeBodyPart.setContent(messageText, "text/html; charset=utf-8")

    val multipart: Multipart = MimeMultipart()
    multipart.addBodyPart(mimeBodyPart)

    message.setContent(multipart)

    Transport.send(message)


    try {

    } catch (e: MessagingException) {
        println(e.message)
        e.printStackTrace()
    }


}