package com.erdemserhat.service.configurations

import com.erdemserhat.routes.admin.*
import com.erdemserhat.routes.article.getAllArticlesV1
import com.erdemserhat.routes.article.getArticleCategoriesV1
import com.erdemserhat.routes.quote.*
import com.erdemserhat.routes.quote.get_quotes.getQuotesV1
import com.erdemserhat.routes.quote.get_quotes.getQuotesV2
import com.erdemserhat.routes.user.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Configures the routing for the application.
 */
fun Application.configureRouting() {
    routing {
        // Default root endpoint
        get("/") {
            call.respond("Harmony Haven Server")
        }

        // Configure versioned API routes
        versionedApiRoutes()
    }


// ...
    routing {
        install(CORS) {
            anyHost() // Allow requests from any host. Replace with specific hosts in production.
            allowMethod(HttpMethod.Options)
            allowMethod(HttpMethod.Put)
            allowMethod(HttpMethod.Delete)
            allowMethod(HttpMethod.Get)
            allowMethod(HttpMethod.Patch)
            allowHeader(HttpHeaders.Authorization)
            allowHeader("cart_session")
            exposeHeader("cart_session")
            allowHeader("MyCustomHeader")
            allowHeader("X-Requested-With")
            allowHeader("X-HTTP-Method-Override")
            allowHeader("Content-Type")
            allowHeader("Authorization")
            allowHeader("Accept")
            allowHeader("Access-Control-Allow-Credentials")
            allowHeader("Accept")
            allowHeader("Access-Control-Allow-Origin")
        }
       // swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")
    }
}

/**
 * Defines versioned API routes.
 */
@OptIn(DelicateCoroutinesApi::class)
fun Route.versionedApiRoutes() {
    // Version 1 API routes
    route("/api/v1") {
        // User Routes
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
        /**
         * Use this section when updating an endpoint for a new feature.
         * Changing v2 endpoints may require clients using them to do significant work.
         */
    }

    // Add more versions as needed
}
