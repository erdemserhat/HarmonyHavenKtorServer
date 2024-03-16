package com.erdemserhat.plugins

import com.erdemserhat.database.FTPConfig
import com.erdemserhat.models.FTPModel
import io.ktor.http.*
import io.ktor.server.application.*
import org.apache.commons.net.ftp.FTP
import org.apache.commons.net.ftp.FTPClient
import java.io.File
import java.io.FileInputStream

fun Application.configureFTP(){

    val ftpServer = environment.config.property("harmonyhaven.ftp.host").getString()
    val ftpUsername = environment.config.property("harmonyhaven.ftp.username").getString()
    val ftpPassword = environment.config.property("harmonyhaven.ftp.password").getString()

    val ftpModel = FTPModel(ftpServer,ftpUsername,ftpPassword)

    println(ftpModel.toString())

    FTPConfig.init(ftpModel)



}


