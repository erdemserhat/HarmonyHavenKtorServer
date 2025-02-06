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

    // Route to retrieve all articles
    get("/articles") {
        // Check if user is authenticated
        val principal = call.principal<JWTPrincipal>()
        val articles = DatabaseModule.articleRepository.getAllArticles()
        call.respond(articles)


    }


// Route to get an article by ID
    get("articles/{id}") {
        val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid ID")
        val article = DatabaseModule.articleRepository.getArticle(id.toInt())

        if (article != null) {
            // User-Agent kontrolü
            val userAgent = call.request.headers["User-Agent"]?.lowercase() ?: ""
            val isSocialMediaBot = userAgent
                .contains("whatsapp") ||
                    userAgent.contains("telegram") ||
                    userAgent.contains("facebook") ||
                    userAgent.contains("twitter")

            if (isSocialMediaBot) {
                // Sosyal medya botları için HTML
                val html = """
                <!DOCTYPE html>
                <html lang="tr">
                <head>
                    <meta charset="UTF-8">
                    <title>${article.title} - Harmony Haven</title>
                    
                    <!-- OpenGraph meta etiketleri -->
                    <meta property="og:title" content="${article.title}">
                    <meta property="og:description" content="${article.content.take(200) + "..."}">
                    <meta property="og:image" content="${article.imagePath}">
                    <meta property="og:url" content="https://harmonyhaven.erdemserhat.com/articles/$id">
                    <meta property="og:type" content="article">
                    <meta property="og:site_name" content="Harmony Haven">
                    
                    <!-- Twitter Card meta etiketleri -->
                    <meta name="twitter:card" content="summary_large_image">
                    <meta name="twitter:title" content="${article.title}">
                    <meta name="twitter:description" content="${article.content.take(200) + "..."}">
                    <meta name="twitter:image" content="${article.imagePath}">
                    
                    <!-- Diğer meta etiketleri -->
                    <meta name="description" content="${article.content.take(200) + "..."}">
                    <meta property="og:locale" content="tr_TR">
                    <link rel="icon" type="image/png" href="https://harmonyhaven.erdemserhat.com/ico.png">
                </head>
                <body>
                    <script>
                        window.location.href = "https://harmonyhaven.erdemserhat.com/articles/$id";
                    </script>
                </body>
                </html>
            """.trimIndent()

                call.respondText(html, ContentType.Text.Html)
            } else {
                // Normal istekler için JSON
                call.respond(
                    status = HttpStatusCode.OK,
                    message = article
                )
            }
        } else {
            call.respond(HttpStatusCode.NotFound)
        }
    }

    // Route to get article meta information
    get("articles/{id}/meta") {
        try {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid ID")
            val article = DatabaseModule.articleRepository.getArticle(id.toInt())

            if (article != null) {
                val meta = mapOf(
                    "title" to article.title,
                    "description" to (article.content.take(200) + "..."),
                    "image" to article.imagePath,
                    "url" to "https://harmonyhaven.erdemserhat.com/articles/$id"
                )
                call.respond(meta)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError)
        }
    }

    get("articles/{id}/preview") {
        val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
        val article = DatabaseModule.articleRepository.getArticle(id.toInt())

        if (article != null) {
            val html = """
            <!DOCTYPE html>
            <html lang="tr">
            <head>
                <meta charset="UTF-8">
                <title>${article.title} - Harmony Haven</title>
                
                <!-- Open Graph meta tags -->
                <meta property="og:title" content="${article.title}">
                <meta property="og:description" content="${article.contentPreview}">
                <meta property="og:image" content="${article.imagePath}">
                <meta property="og:url" content="https://harmonyhaven.erdemserhat.com/articles/${id}">
                <meta property="og:type" content="article">
                <meta property="og:site_name" content="Harmony Haven">
                
                <!-- Twitter Card meta tags -->
                <meta name="twitter:card" content="summary_large_image">
                <meta name="twitter:title" content="${article.title}">
                <meta name="twitter:description" content="${article.contentPreview}">
                <meta name="twitter:image" content="${article.imagePath}">
            </head>
            <body>
                <script>
                    window.location.href = "https://harmonyhaven.erdemserhat.com/articles/${id}";
                </script>
            </body>
            </html>
        """.trimIndent()

            call.respondText(html, ContentType.Text.Html)
        } else {
            call.respond(HttpStatusCode.NotFound)
        }
    }

    get("articles/{id}/{slug}") {
        val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
        val slug = call.parameters["slug"] ?: return@get call.respond(HttpStatusCode.BadRequest)
        val article = DatabaseModule.articleRepository.getArticle(id.toInt())

        if (article != null && article.slug == slug) {

            val html = """
            <!DOCTYPE html>
            <html lang="tr">
            <head>
                <meta charset="UTF-8">
                <title>${article.title} - Harmony Haven</title>
                
                <!-- Open Graph meta tags -->
                <meta property="og:title" content="${article.title}">
                <meta property="og:description" content="${article.contentPreview}">
                <meta property="og:image" content="${article.imagePath}">
                <meta property="og:url" content="https://harmonyhaven.erdemserhat.com/articles/${id}/${slug}">
                <meta property="og:type" content="article">
                <meta property="og:site_name" content="Harmony Haven">
                
                <!-- Twitter Card meta tags -->
                <meta name="twitter:card" content="summary_large_image">
                <meta name="twitter:title" content="${article.title}">
                <meta name="twitter:description" content="${article.contentPreview}">
                <meta name="twitter:image" content="${article.imagePath}">
            </head>
            <body>
                <script>
                    window.location.href = "https://harmonyhaven.erdemserhat.com/articles/${id}/${slug}";
                </script>
            </body>
            </html>
        """.trimIndent()

            call.respondText(html, ContentType.Text.Html)
        } else {

            val html = """
            <!DOCTYPE html>
            <html lang="tr">
            <head>
                <meta charset="UTF-8">
                <title>Makale bulunamadı - Harmony Haven</title>
                
                <!-- Open Graph meta tags -->
                <meta property="og:title" content="Makale bulunamadı">
                <meta property="og:description" content="Aradığınız makale bulunamadı veya URL hatalı. Harmony Haven'da daha fazla içerik keşfedin.">
                <meta property="og:image" content="https://harmonyhaven.erdemserhat.com/static/default-preview-image.png">
                <meta property="og:url" content="https://harmonyhaven.erdemserhat.com/articles/${id}/${slug}">
                <meta property="og:type" content="website">
                <meta property="og:site_name" content="Harmony Haven">
                
                <!-- Twitter Card meta tags -->
                <meta name="twitter:card" content="summary_large_image">
                <meta name="twitter:title" content="Makale bulunamadı">
                <meta name="twitter:description" content="Aradığınız makale bulunamadı veya URL hatalı. Harmony Haven'da daha fazla içerik keşfedin.">
                <meta name="twitter:image" content="https://harmonyhaven.erdemserhat.com/static/default-preview-image.png">
            </head>
            <body>
                <script>
                    window.location.href = "https://harmonyhaven.erdemserhat.com/";
                </script>
            </body>
            </html>
        """.trimIndent()

            call.respondText(html, ContentType.Text.Html)
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
