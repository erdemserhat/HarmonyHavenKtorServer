package com.erdemserhat.romote.database

/**
 * Data class representing database configuration properties.
 * Contains information such as host, database name, username, password, port, and SSL usage.
 */
data class DatabaseModel(
    val host: String,
    val databaseName: String,
    val username: String,
    val password: String,
    val port: String,
    val useSSL: String
)
