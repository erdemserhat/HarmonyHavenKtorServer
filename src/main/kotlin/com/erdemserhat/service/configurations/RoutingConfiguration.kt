package com.erdemserhat.service.configurations

import com.erdemserhat.routes.admin.TestNotificationV1
import com.erdemserhat.routes.admin.deleteUserAdminV1
import com.erdemserhat.routes.admin.openAIRequestV1
import com.erdemserhat.routes.admin.sendNotificationSpecificV1
import com.erdemserhat.routes.article.getAllArticlesV1
import com.erdemserhat.routes.category.readCategoriesV1
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
        readCategoriesV1()
        getAllArticlesV1()

        // Firebase Notification Service Routes
        sendNotificationSpecificV1()

        // Admin Routes
        deleteUserAdminV1()


        //openai routes
        openAIRequestV1()
        TestNotificationV1()

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
