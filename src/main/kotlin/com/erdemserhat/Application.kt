package com.erdemserhat


import com.erdemserhat.plugins.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.application.ApplicationCallPipeline.ApplicationPhase.Plugins
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import java.util.concurrent.atomic.AtomicInteger


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
    configureTemplating()
    configureSMTP()
    configureRemoteDatabase()










    val requestCounter = mutableMapOf<String, AtomicInteger>()

    // Middleware to limit requests from a single IP address
    intercept(Plugins) {
        val ip = call.request.origin.remoteHost
        val count = requestCounter.getOrPut(ip) { AtomicInteger(0) }.incrementAndGet()

        if (count > 10) { // Limit requests from each IP address to 10
            call.respond(HttpStatusCode.TooManyRequests, "Too many requests from this IP address")
            finish()
        }
    }






}


