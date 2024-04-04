package com.erdemserhat.routes.category

import com.erdemserhat.service.di.DatabaseModule
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * Defines routes for reading categories.
 */
fun Route.readCategoriesV1(){
    // Route to retrieve all categories
    get("/categories"){
        // Respond with all categories from the repository
        call.respond(DatabaseModule.categoryRepository.getAllCategory())
    }
}
