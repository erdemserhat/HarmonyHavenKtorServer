package com.erdemserhat.routes.category

import com.erdemserhat.di.DatabaseModule
import com.erdemserhat.di.DatabaseModule.categoryRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.readCategories(){
    get("/categories"){
        call.respond(categoryRepository.getAllCategory())

    }


}