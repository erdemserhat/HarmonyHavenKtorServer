package com.erdemserhat.routes.quote.v1.category

import com.erdemserhat.models.QuoteCategory
import com.erdemserhat.service.di.DatabaseModule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.Identity.decode

fun Route.deleteQuoteCategoryV1(){
    delete("/delete-quote-category/{categoryId}") {
        val categoryId = call.parameters["categoryId"]?.toIntOrNull()
        if (categoryId != null) {
            DatabaseModule.quoteCategoryRepository.removeCategory(categoryId)
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.BadRequest, "Invalid categoryId")
        }
    }
}