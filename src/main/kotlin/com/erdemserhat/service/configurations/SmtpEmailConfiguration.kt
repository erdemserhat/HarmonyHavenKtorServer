package com.erdemserhat.service.configurations

import com.erdemserhat.data.mail.EmailConfig
import com.erdemserhat.data.mail.SMTPModel
import io.ktor.server.application.*

/**
 * Configures the SMTP settings for sending emails.
 * Retrieves SMTP configuration parameters from the application environment and initializes the EmailConfig.
 */
fun Application.configureSMTP() {
    // Retrieve SMTP configuration parameters from the application environment
    val smtpHost = environment.config.property("harmonyhaven.email.smtpHost").getString()
    val smtpPort = environment.config.property("harmonyhaven.email.smtpPort").getString()
    val username = environment.config.property("harmonyhaven.email.username").getString()
    val password = environment.config.property("harmonyhaven.email.password").getString()
    val smtpAuthEnabled = environment.config.property("harmonyhaven.email.smtpAuthEnabled").getString()
    val starttlsEnabled = environment.config.property("harmonyhaven.email.startTLSEnabled").getString()

    // Create an SMTPModel object with the retrieved parameters
    val smtpModel = SMTPModel(
        host = smtpHost,
        port = smtpPort,
        username = username,
        password = password,
        starttlsEnabled = starttlsEnabled,
        smtpAuthEnabled = smtpAuthEnabled
    )

    // Initialize the EmailConfig with the SMTPModel
    EmailConfig.init(smtpModel)
}
