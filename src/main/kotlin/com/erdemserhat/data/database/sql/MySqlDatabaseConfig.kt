package com.erdemserhat.data.database.sql

import org.ktorm.database.Database

/**
 * Object responsible for configuring and initializing the database connection using KTorm.
 */
object MySqlDatabaseConfig {

    lateinit var ktormDatabase: Database

    // Database connection parameters
    private lateinit var HOST: String
    private lateinit var DATABASE_NAME: String
    private lateinit var USERNAME: String
    private lateinit var PASSWORD: String
    private lateinit var PORT: String
    private var USE_SSL: Boolean = false  // Use Boolean instead of String

    /**
     * Initializes the database connection with the provided [MySqlCredentialsModel].
     * @param mySqlCredentialsModel The database configuration model containing connection parameters.
     */
    fun init(mySqlCredentialsModel: MySqlCredentialsModel) {
        HOST = mySqlCredentialsModel.host
        DATABASE_NAME = mySqlCredentialsModel.databaseName
        USERNAME = mySqlCredentialsModel.username
        PASSWORD = mySqlCredentialsModel.password
        PORT = mySqlCredentialsModel.port
        USE_SSL = mySqlCredentialsModel.useSSL.toBoolean() // Convert to Boolean

        // Construct JDBC URL
        val JDBC_URL = "jdbc:mysql://$HOST:$PORT/$DATABASE_NAME?user=$USERNAME&password=$PASSWORD&useSSL=$USE_SSL"



        // Initialize KTorm database connection
        ktormDatabase = Database.connect(JDBC_URL)
    }
}
