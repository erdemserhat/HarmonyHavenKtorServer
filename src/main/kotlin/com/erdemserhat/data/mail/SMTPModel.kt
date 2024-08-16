package com.erdemserhat.data.mail

/**
 * Data class representing SMTP server configuration properties.
 * Contains information such as host, port, username, password, SMTP authentication, and StartTLS enablement.
 */
data class SMTPModel(
    val host: String,
    val port: String,
    val username: String,
    val password: String,
    val smtpAuthEnabled: String,
    val starttlsEnabled: String
)
