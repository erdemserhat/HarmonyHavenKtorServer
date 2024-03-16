package com.erdemserhat.plugins

import com.erdemserhat.database.FTPConfig
import com.erdemserhat.models.appconfig.FTPModel
import io.ktor.server.application.*

fun Application.configureFTP(){

    val ftpServer = environment.config.property("harmonyhaven.ftp.host").getString()
    val ftpUsername = environment.config.property("harmonyhaven.ftp.username").getString()
    val ftpPassword = environment.config.property("harmonyhaven.ftp.password").getString()

    val ftpModel = FTPModel(ftpServer,ftpUsername,ftpPassword)

    println(ftpModel.toString())

    FTPConfig.init(ftpModel)



}


