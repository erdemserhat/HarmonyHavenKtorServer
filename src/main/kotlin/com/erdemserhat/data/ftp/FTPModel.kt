package com.erdemserhat.data.ftp

/**
 * Data class representing FTP server configuration properties.
 * Contains information such as server address, username, and password.
 */
data class FTPModel(
    val server: String,
    val username: String,
    val password: String
)
