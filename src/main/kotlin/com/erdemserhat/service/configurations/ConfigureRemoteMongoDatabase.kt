package com.erdemserhat.service.configurations

import com.erdemserhat.data.database.nosql.MongoDatabaseConfig
import io.ktor.server.application.*

fun Application.configureRemoteMongoDatabase() {

    val mongoConnectionUrl = environment.config.property("mongodb.url").getString()
    val mongoDatabaseName = environment.config.property("mongodb.database").getString()

    MongoDatabaseConfig.init(
        connectionUrl = mongoConnectionUrl,
        databaseName = mongoDatabaseName,
    )
}