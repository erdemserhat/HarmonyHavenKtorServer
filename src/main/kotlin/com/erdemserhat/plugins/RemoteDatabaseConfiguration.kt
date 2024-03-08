package com.erdemserhat.plugins

import com.erdemserhat.database.DatabaseConfig
import com.erdemserhat.models.DatabaseModel
import io.ktor.server.application.*

fun Application.configureRemoteDatabase(){
    val host = environment.config.property("harmonyhaven.database1.host").getString()
    val databaseName= environment.config.property("harmonyhaven.database1.database").getString()
    val username= environment.config.property("harmonyhaven.database1.username").getString()
    val password= environment.config.property("harmonyhaven.database1.password").getString()
    val port = environment.config.property("harmonyhaven.database1.port").getString()
    val useSSL= environment.config.property("harmonyhaven.database1.useSSL").getString()

    val databaseModel = DatabaseModel(host,databaseName,username,password,port,useSSL)

    DatabaseConfig.init(databaseModel)


}