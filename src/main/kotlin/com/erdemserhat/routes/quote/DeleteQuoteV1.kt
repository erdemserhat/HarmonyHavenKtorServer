package com.erdemserhat.routes.quote

import com.erdemserhat.models.Quote
import com.erdemserhat.service.di.DatabaseModule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteQuotes(){
    delete("/delete-quotes") {
            DatabaseModule.quoteRepository.deleteAll()
        call.respond(
            status = HttpStatusCode.OK,
            message = mapOf("successful" to true)
        )

    }
}
