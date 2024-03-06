package com.example


import com.example.plugins.*
import com.example.repository.MySQLUserRepository
import com.example.repository.UserRepository
import io.ktor.server.application.*



fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureRouting()




}
