package com.erdemserhat

import com.erdemserhat.di.AuthenticationModule.tokenConfigSecurity
import com.erdemserhat.di.DatabaseModule.articleRepository

import com.erdemserhat.models.Article

import com.erdemserhat.plugins.*
import com.erdemserhat.security.hashing.SHA256HashingService
import com.erdemserhat.security.token.JwtTokenService
import com.erdemserhat.security.token.TokenConfig

import io.ktor.server.application.*
import java.io.File
import java.io.FileOutputStream


fun main(args: Array<String>) {
    //Class.forName("com.mysql.jdbc.Driver")
    io.ktor.server.netty.EngineMain.main(args)




}

fun Application.module() {

    // use this area for testing.............




    configureSerialization()
    configureTemplating()
    configureSMTP()
    configureFTP()
    configureRemoteDatabase()
    configureFirebase()
    configureTokenConfig()
    configureSecurity(tokenConfigSecurity)
    configureRouting()








}




fun createSampleFile(): File {
    val fileContent = "Bu örnek dosyanın içeriğidir."
    val file = File("sample.txt")
    FileOutputStream(file).use { stream ->
        stream.write(fileContent.toByteArray())
    }
    return file
}




