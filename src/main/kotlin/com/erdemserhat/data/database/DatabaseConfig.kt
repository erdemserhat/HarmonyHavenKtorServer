package com.erdemserhat.data.database

import org.ktorm.database.Database

/**
 * Object responsible for configuring and initializing the database connection using KTorm.
 */
object DatabaseConfig {

     lateinit var ktormDatabase: Database

    // Database connection parameters
    private lateinit var HOST: String
    private lateinit var DATABASE_NAME: String
    private lateinit var USERNAME: String
    private lateinit var PASSWORD: String
    private lateinit var PORT: String
    private lateinit var USE_SSL: String

    /**
     * Initializes the database connection with the provided [DatabaseModel].
     * @param databaseModel The database configuration model containing connection parameters.
     */
    fun init(databaseModel: DatabaseModel) {
        HOST = databaseModel.host
        DATABASE_NAME = databaseModel.databaseName
        USERNAME = databaseModel.username
        PASSWORD = databaseModel.password
        PORT = databaseModel.port
        USE_SSL = databaseModel.useSSL

        // Construct JDBC URL
        val JDBC_URL = "jdbc:mysql://$HOST:$PORT/$DATABASE_NAME?user=$USERNAME&password=$PASSWORD&useSSL=$USE_SSL"
        println("JDBC URL: $JDBC_URL")

        // Initialize KTorm database connection
        ktormDatabase = Database.connect(JDBC_URL)
    }
}
