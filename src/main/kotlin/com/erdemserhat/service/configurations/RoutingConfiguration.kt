package com.erdemserhat.service.configurations


import com.erdemserhat.data.PersistentVersionStorage
import com.erdemserhat.routes.admin.*
import com.erdemserhat.routes.article.getAllArticlesV1
import com.erdemserhat.routes.article.getArticleCategoriesV1
import com.erdemserhat.routes.comment.commentRoutes
import com.erdemserhat.routes.quote.*
import com.erdemserhat.routes.quote.get_quotes.getQuotesV1
import com.erdemserhat.routes.quote.get_quotes.getQuotesV2
import com.erdemserhat.routes.quote.get_quotes.getQuotesV3
import com.erdemserhat.routes.user.*
import com.erdemserhat.service.di.DatabaseModule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sse.*
import kotlinx.coroutines.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

// Helper function to generate initial logs
fun generateInitialLogs(): String {
    val initialLogs = listOf(
        "Starting Harmony Haven Server...",
        "Loading configuration files...",
        "Establishing database connection...",
        "Database connection established successfully.",
        "WebSocket service initialized on port 8080."
    )
    return initialLogs.joinToString("\n") { "<div class='log'>$it</div>" }
}

/**
 * Configures the routing for the application.
 */
fun Application.configureRouting() {
    routing {
        static("/static") { // Serves files under /static path
            resources("static")
        }
        // Default root endpoint
        get("/") {
            call.respondText(
                staticMainRouteHtmlContent.trimIndent(),
                ContentType.Text.Html
            )
        }

        install(SSE)


        get("/presentation") {

            call.respondRedirect("/static/presentation/index.html")

        }

        get("/x") {
            call.respondRedirect("/static/x/index.html")

        }




        get("/vitalis") {
            call.respondRedirect("/static/vitalis/index.html")

        }

        get("/asteriatech") {
            call.respondRedirect("/static/asteriatech/index.html")

        }

        get("/portfolio") {
            call.respondRedirect("/static/portfolio/index.html")

        }


        get("/check-android-version/{version}") {

            val latestVersion = PersistentVersionStorage.getVersionCode()
            val currentVersion = call.parameters["version"]
            if (currentVersion != null) {
                if (latestVersion > currentVersion.toInt()) {
                    call.respond(mapOf("result" to 0))

                } else {
                    call.respond(mapOf("result" to 1))
                }
            }


        }
        authenticate {
            get("/update-version-code/{version}") {
                val principal = call.principal<JWTPrincipal>()
                val role = principal?.payload?.getClaim("role")?.asString()
                // Receive email data from the request body
                val updatedVersion = call.parameters["version"]
                // Check if the authenticated user has admin role
                if (role != "admin") {
                    call.respond(
                        status = HttpStatusCode.Unauthorized,
                        message = "You are not allowed to use this service"
                    )
                    return@get
                } else if (updatedVersion != null) {
                    PersistentVersionStorage.setVersionCode(updatedVersion.toInt())
                    call.respond(HttpStatusCode.OK)
                } else {
                    call.respond(status = HttpStatusCode.InternalServerError, message = "version code is not valid")
                }


            }

        }

        // Configure versioned API routes
        versionedApiRoutes()
    }

    // Helper function to generate a flowing "code-like" background


// ...
}

/**
 * Defines versioned API routes.
 */
@OptIn(DelicateCoroutinesApi::class)
fun Route.versionedApiRoutes() {
    // Version 1 API routes
    route("/api/v1") {
        // User Routes
        chatting()
        googleLogin()
        registerUserV1()
        authenticateUserV1()
        //deleteUserV1()
        updateUserV1()
        resetPasswordV1()
        fcmEnrolment()
        getNotifications()
        getUserInformation()

        // Article Routes
        getArticleCategoriesV1()
        getAllArticlesV1()

        // Firebase Notification Service Routes
        sendNotificationSpecificV1()
        sendNotificationGeneralV1()

        // Admin Routes
        //deleteUserAdminV1()


        //openai routes
        //openAIRequestV1()
        //TestNotificationV1()


        //Quotes routes
        addQuoteV1()

        deleteQuotes()
        getQuotesV1()
        likeQuote()
        removeLikeQuoteV1()
        getAllLikedQuotesV1()
        //updateQuoteV1()
        commentRoutes()

        //quotes category

        authenticate {
            get("check-auth-status") {
                call.respond(
                    status = HttpStatusCode.OK,
                    message = "ok"
                )

                GlobalScope.launch(Dispatchers.IO) {
                    val principal = call.principal<JWTPrincipal>()
                    val email = principal?.payload?.getClaim("email")?.asString()!!

                    // Get the IP address from the request
                    val ipAddress = call.request.origin.remoteHost

                    // Get the current date and time and format it in English
                    val currentDateTime = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss", Locale.ENGLISH)
                    val formattedDateTime = currentDateTime.format(formatter)

                    // Construct the log message
                    val msg = "$email opened the app. Date and time: $formattedDateTime, IP Address: $ipAddress"
                    println(msg)
                }

            }
        }


    }

    // Version 2 API routes
    route("/api/v2") {

        getQuotesV2()
        /**
         * Use this section when updating an endpoint for a new feature.
         * Changing v1 endpoints may require clients using them to do significant work.
         */
    }

    // Version 3 API routes
    route("/api/v3") {
        get("/nondb") {
            call.respond(status = HttpStatusCode.OK, "ok")
        }
        get("/db") {
            val article = DatabaseModule.articleRepository.getAllArticles()
            call.respond(status = HttpStatusCode.OK, "ok")
        }



        getQuotesV3()
        /**
         * Use this section when updating an endpoint for a new feature.
         * Changing v2 endpoints may require clients using them to do significant work.
         */
    }

    // Add more versions as needed
}
