package com.erdemserhat.data.mail

import java.util.*
import javax.mail.Authenticator
import javax.mail.PasswordAuthentication
import javax.mail.Session

/**
 * Configuration object for setting up SMTP email functionality.
 */
object EmailConfig {
    lateinit var SESSION: Session
    lateinit var SMTP_USERNAME: String

    private lateinit var SMTP_HOST: String
    private lateinit var SMTP_PORT: String
    private lateinit var SMTP_PASSWORD: String
    private lateinit var SMTP_AUTH_ENABLED: String
    private lateinit var START_TLS_ENABLED: String
    private lateinit var PROPERTIES: Properties

    /**
     * Initializes the email configuration.
     * @param smtpModel The SMTP model containing configuration parameters.
     */
    fun init(smtpModel: SMTPModel) {
        SMTP_HOST = smtpModel.host
        SMTP_PORT = smtpModel.port
        SMTP_USERNAME = smtpModel.username
        SMTP_PASSWORD = smtpModel.password
        SMTP_AUTH_ENABLED = smtpModel.smtpAuthEnabled
        START_TLS_ENABLED = smtpModel.starttlsEnabled

        // Set up properties for the mail session
        PROPERTIES = Properties().apply {
            put("mail.smtp.auth", SMTP_AUTH_ENABLED)
            put("mail.smtp.starttls.enable", START_TLS_ENABLED)
            put("mail.smtp.host", SMTP_HOST)
            put("mail.smtp.port", SMTP_PORT)
        }

        // Create the mail session with authentication
        SESSION = Session.getInstance(PROPERTIES, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD)
            }
        })
    }
}
