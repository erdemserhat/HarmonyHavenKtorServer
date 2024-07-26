package com.erdemserhat.routes.quote.category

import com.erdemserhat.models.QuoteCategory
import com.erdemserhat.service.di.DatabaseModule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getQuoteCategoryV1(){
    get("/get-quote-category") {
        val categories =DatabaseModule.quoteCategoryRepository.getCategories()
        call.respond(
            message = categories,
            status=HttpStatusCode.OK)
    }

}