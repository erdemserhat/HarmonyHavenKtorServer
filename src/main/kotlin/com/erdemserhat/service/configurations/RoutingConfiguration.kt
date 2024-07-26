package com.erdemserhat.service.configurations

import com.erdemserhat.routes.admin.TestNotificationV1
import com.erdemserhat.routes.admin.deleteUserAdminV1
import com.erdemserhat.routes.admin.openAIRequestV1
import com.erdemserhat.routes.admin.sendNotificationSpecificV1
import com.erdemserhat.routes.article.getAllArticlesV1
import com.erdemserhat.routes.article.getArticleCategoriesV1
import com.erdemserhat.routes.quote.category.addQuoteCategoryV1
import com.erdemserhat.routes.quote.category.deleteQuoteCategoryV1
import com.erdemserhat.routes.quote.category.getQuoteCategoryV1
import com.erdemserhat.routes.quote.category.updateQuoteCategoryV1
import com.erdemserhat.routes.quote.quote.addQuoteV1
import com.erdemserhat.routes.quote.quote.deleteQuoteV1
import com.erdemserhat.routes.quote.quote.getQuotesByCategoryV1
import com.erdemserhat.routes.quote.quote.updateQuoteV1
import com.erdemserhat.routes.user.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

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
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")
    }
}

/**
 * Defines versioned API routes.
 */
fun Route.versionedApiRoutes() {
    // Version 1 API routes
    route("/api/v1") {
        // User Routes
        registerUserV1()
        authenticateUserV1()
        deleteUserV1()
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

        // Admin Routes
        deleteUserAdminV1()


        //openai routes
        openAIRequestV1()
        TestNotificationV1()


        //Quotes routes
        addQuoteV1()
        deleteQuoteV1()
        getQuotesByCategoryV1()
        updateQuoteV1()

        //quotes category
        addQuoteCategoryV1()
        deleteQuoteCategoryV1()
        getQuoteCategoryV1()
        updateQuoteCategoryV1()

        authenticate {
            get("check-auth-status") {
                call.respond(
                    status = HttpStatusCode.OK,
                    message = "ok"
                )
            }
        }

    }

    // Version 2 API routes
    route("/api/v2") {
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
