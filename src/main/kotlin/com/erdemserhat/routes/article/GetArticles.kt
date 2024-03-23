package com.erdemserhat.routes.article

import com.erdemserhat.di.DatabaseModule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import javax.xml.crypto.Data

fun Route.getAllArticles(){
    get("/articles"){
        call.respond(DatabaseModule.articleRepository.getAllArticles())

    }
    get("articles/{id}") {
        val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid ID")
        val article = DatabaseModule.articleRepository.getArticle(id.toInt())
        if (article != null) {
            call.respond(article)
        } else {
            call.respond(HttpStatusCode.NotFound, "Article not found")
        }
    }

    get("articles/recent/{size}") {
        try {
            val size= call.parameters["size"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid Size")
            val article = DatabaseModule.articleRepository.getAllArticles().takeLast(size.toInt())
            call.respond(article)
        }catch (_:Exception){
            call.respond(HttpStatusCode.BadRequest)
        }

    }

    get("articles/category/{id}"){
        try{
            val categoryId = call.parameters["id"]?:return@get call.respond(HttpStatusCode.BadRequest,"Invalid Category ID")
            val articles = DatabaseModule.articleRepository.getArticlesByCategory(categoryId.toInt())
            call.respond(articles)
        }catch (_:Exception){
            call.respond(HttpStatusCode.BadRequest)
        }
    }



}