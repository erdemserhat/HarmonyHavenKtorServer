package com.erdemserhat.service.configurations

import com.erdemserhat.romote.database.DatabaseConfig
import com.erdemserhat.romote.database.DatabaseModel
import io.ktor.server.application.*

/**
 * Configures a remote database connection for the application.
 * This function reads database configuration from environment properties and initializes the database configuration.
 */
fun Application.configureRemoteDatabase() {
    // Read database configuration from environment properties
    val host = environment.config.property("harmonyhaven.database1.host").getString()
    val databaseName = environment.config.property("harmonyhaven.database1.database").getString()
    val username = environment.config.property("harmonyhaven.database1.username").getString()
    val password = environment.config.property("harmonyhaven.database1.password").getString()
    val port = environment.config.property("harmonyhaven.database1.port").getString()
    val useSSL = environment.config.property("harmonyhaven.database1.useSSL").getString()

    // Create a DatabaseModel with the read configuration
    val databaseModel = DatabaseModel(host, databaseName, username, password, port, useSSL)

    // Initialize the DatabaseConfig with the DatabaseModel
    DatabaseConfig.init(databaseModel)
}
