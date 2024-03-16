package com.erdemserhat.database

import com.erdemserhat.models.appconfig.DatabaseModel
import org.ktorm.database.Database

object DatabaseConfig {

    lateinit var ktormDatabase: Database

    private lateinit var HOST: String
    private lateinit var DATABASE_NAME: String
    private lateinit var USERNAME: String
    private lateinit var PASSWORD: String
    private lateinit var PORT: String
    private lateinit var USE_SSL: String
    private lateinit var JDBC_URL: String

    fun init(databaseModel: DatabaseModel) {
        HOST = databaseModel.host
        DATABASE_NAME = databaseModel.databaseName
        USERNAME = databaseModel.username
        PASSWORD = databaseModel.password
        PORT = databaseModel.port
        USE_SSL = databaseModel.useSSL
        JDBC_URL = "jdbc:mysql://$HOST:$PORT/$DATABASE_NAME?user=$USERNAME&password=$PASSWORD&useSSL=$USE_SSL"
        println(JDBC_URL)
        ktormDatabase = Database.connect(JDBC_URL)

    }

}