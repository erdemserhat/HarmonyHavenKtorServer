package com.erdemserhat.data.database.sql

/**
 * Data class representing database configuration properties.
 * Contains information such as host, database name, username, password, port, and SSL usage.
 */
data class MySqlCredentialsModel(
    val host: String,
    val databaseName: String,
    val username: String,
    val password: String,
    val port: String,
    val useSSL: String
)
