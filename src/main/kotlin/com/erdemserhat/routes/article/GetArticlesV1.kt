package com.erdemserhat.routes.article

import com.erdemserhat.service.di.DatabaseModule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import javax.xml.crypto.Data

/**
 * Defines the routes related to article retrieval.
 */
fun Route.getAllArticlesV1() {
    // Authenticate user to access these routes
    authenticate {
        // Route to retrieve all articles
        get("/articles") {
            // Check if user is authenticated
            val principal = call.principal<JWTPrincipal>()
            val articles = DatabaseModule.articleRepository.getAllArticles()
            call.respond(articles)

        }
    }

    // Route to get an article by ID
    get("articles/{id}") {
        println("worked")
        // Retrieve article ID from path parameters
        val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid ID")
        // Retrieve article from repository
        val article = DatabaseModule.articleRepository.getArticle(id.toInt())
        // Respond with the article if found, otherwise return 404
        if (article != null) {
            call.respond(
                status = HttpStatusCode.OK,
                message = article
            )
        } else {
            call.respond(
                HttpStatusCode.NotFound
            )
        }
    }

    // Route to get recent articles by specifying size
    get("articles/recent/{size}") {
        try {
            // Retrieve size parameter from path parameters
            val size = call.parameters["size"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid Size")
            // Retrieve recent articles from repository
            val article = DatabaseModule.articleRepository.getAllArticles().takeLast(size.toInt())
            call.respond(article)
        } catch (_: Exception) {
            // Error occurred while retrieving articles
            call.respond(HttpStatusCode.BadRequest)
        }
    }

    // Route to get articles by category ID
    get("articles/category/{id}") {
        try {
            // Retrieve category ID from path parameters
            val categoryId =
                call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid Category ID")
            // Retrieve articles by category from repository
            val articles = DatabaseModule.articleRepository.getArticlesByCategory(categoryId.toInt())
            call.respond(articles)
        } catch (_: Exception) {
            // Error occurred while retrieving articles by category
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}
