package com.erdemserhat.routes.quote

import com.erdemserhat.service.di.DatabaseModule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getQuotes() {
    get("/get-quotes") {
        val quotes = DatabaseModule.quoteRepository.getQuotes()
        call.respond(HttpStatusCode.OK, quotes)
    }
}
