package com.erdemserhat.service.configurations

import com.erdemserhat.routes.admin.TestNotificationV1
import com.erdemserhat.routes.admin.deleteUserAdminV1
import com.erdemserhat.routes.admin.openAIRequestV1
import com.erdemserhat.routes.admin.sendNotificationSpecificV1
import com.erdemserhat.routes.article.getAllArticlesV1
import com.erdemserhat.routes.article.getArticleCategoriesV1
import com.erdemserhat.routes.quote.v1.category.addQuoteCategoryV1
import com.erdemserhat.routes.quote.v1.category.deleteQuoteCategoryV1
import com.erdemserhat.routes.quote.v1.category.getQuoteCategoryV1
import com.erdemserhat.routes.quote.v1.category.updateQuoteCategoryV1
import com.erdemserhat.routes.quote.v1.quote.addQuoteV1
import com.erdemserhat.routes.quote.v1.quote.deleteQuoteV1
import com.erdemserhat.routes.quote.v1.quote.getQuotesByCategoryV1
import com.erdemserhat.routes.quote.v1.quote.updateQuoteV1
import com.erdemserhat.routes.user.*
import io.ktor.server.application.*
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
