package com.example.dao

import com.example.models.Articles
import com.example.models.Users
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseSingleton {
    fun init() {
        val driverClassName = "org.h2.Driver"
        val jdbcURL = "jdbc:h2:file:./build/db"
        val database = Database.connect(jdbcURL, driverClassName)

        transaction(database) {
            //Create Tables Here
            SchemaUtils.create(Articles,Users)

        }
    }

    //This function executes the queries
    suspend fun <T> dbQuery (block:suspend ()->T):T=
        newSuspendedTransaction(Dispatchers.IO) { block() }
}