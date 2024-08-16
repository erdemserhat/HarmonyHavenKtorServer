package com.erdemserhat.service.configurations

import com.erdemserhat.data.ftp.FTPConfig
import com.erdemserhat.data.ftp.FTPModel
import io.ktor.server.application.*

/**
 * Configures FTP settings for the application.
 * This function reads FTP configuration from the environment properties and initializes the FTP configuration.
 */
fun Application.configureFTP() {
    // Read FTP configuration from environment properties
    val ftpServer = environment.config.property("harmonyhaven.ftp.host").getString()
    val ftpUsername = environment.config.property("harmonyhaven.ftp.username").getString()
    val ftpPassword = environment.config.property("harmonyhaven.ftp.password").getString()

    // Create FTP model with the read configuration
    val ftpModel = FTPModel(ftpServer, ftpUsername, ftpPassword)

    // Print FTP model for debugging purposes
    println(ftpModel.toString())

    // Initialize FTP configuration with the FTP model
    FTPConfig.init(ftpModel)
}




/*
fun createSampleFile(): File {
    val fileContent = "Bu örnek dosyanın içeriğidir."
    val file = File("sample.txt")
    FileOutputStream(file).use { stream ->
        stream.write(fileContent.toByteArray())
    }
    return file
}
 */


