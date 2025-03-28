package com.erdemserhat.data.mail

import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart

/**
 * Sends an email using the configured SMTP server.
 * @param to The recipient's email address.
 * @param subject The subject of the email.
 * @param messageText The content of the email message.
 */
fun sendEmail(to: String, subject: String, messageText: String) {
    try {
        // Create a new message using the configured email session
        val message: Message = MimeMessage(EmailConfig.SESSION)

        // Set the sender's email address
        message.setFrom(InternetAddress(EmailConfig.SMTP_USERNAME))

        // Set the recipient's email address
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to))

        // Set the subject of the email
        message.subject = subject

        // Create a new MimeBodyPart to hold the message content
        val mimeBodyPart = MimeBodyPart()

        // Set the content of the MimeBodyPart to the message text with UTF-8 charset
        mimeBodyPart.setContent(messageText, "text/html; charset=UTF-8")

        // Create a new Multipart to hold the MimeBodyPart
        val multipart: Multipart = MimeMultipart()
        multipart.addBodyPart(mimeBodyPart)

        // Set the content of the message to the Multipart
        message.setContent(multipart)

        // Send the message
        Transport.send(message)
    } catch (e: MessagingException) {
        // Handle any exceptions that occur during sending
    }
}