package com.erdemserhat.service.configurations

import com.erdemserhat.routes.admin.*
import com.erdemserhat.routes.article.getAllArticlesV1
import com.erdemserhat.routes.article.getArticleCategoriesV1
import com.erdemserhat.routes.quote.addQuoteV1
import com.erdemserhat.routes.quote.deleteQuotes
import com.erdemserhat.routes.quote.getQuotes
import com.erdemserhat.routes.quote.updateQuoteV1
import com.erdemserhat.routes.user.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate
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
        // Default root endpoint
        get("/") {
            call.respondText(
                """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Harmony Haven Server</title>
                    <style>
                        body {
                            margin: 0;
                            font-family: 'Courier New', Courier, monospace;
                            background: black;
                            color: #00ff00;
                            display: flex;
                            flex-direction: column;
                            justify-content: center;
                            align-items: center;
                            height: 100vh;
                            overflow: hidden;
                        }

                        header {
                            font-size: 2em;
                            font-weight: bold;
                            margin-bottom: 20px;
                            color: #00ff00;
                            text-shadow: 0 0 5px #00ff00, 0 0 20px #00ff00;
                            text-align: center;
                        }

                        .terminal {
                            width: 90%;
                            max-width: 800px;
                            height: 60%;
                            background: #000000;
                            border: 1px solid #00ff00;
                            border-radius: 5px;
                            padding: 10px;
                            box-shadow: 0 0 20px #00ff00, 0 0 40px #00ff00;
                            overflow: hidden;
                            display: flex;
                            flex-direction: column;
                            justify-content: flex-start;
                        }

                        .logs {
                            flex: 2;
                            overflow-y: hidden;
                            display: flex;
                            flex-direction: column;
                            font-size: 1em;
                            line-height: 1.4em;
                        }

                        .status-panel {
                            flex: 1;
                            display: flex;
                            justify-content: space-between;
                            align-items: center;
                            border-top: 1px solid #00ff00;
                            padding-top: 10px;
                            margin-top: 10px;
                            font-size: 0.9em;
                        }

                        .status-item {
                            display: flex;
                            flex-direction: column;
                            align-items: center;
                        }

                        .status-item span {
                            font-weight: bold;
                        }

                        .log {
                            opacity: 0;
                            animation: fadeIn 1s forwards, scrollUp 20s linear infinite;
                        }

                        @keyframes fadeIn {
                            0% { opacity: 0; }
                            100% { opacity: 1; }
                        }

                        footer {
                            margin-top: 10px;
                            font-size: 0.8em;
                            color: #8b949e;
                        }
                    </style>
                </head>
                <body>
                    <header>Harmony Haven Server</header>
                    <div class="terminal">
                        <div class="logs" id="logs">
                            ${generateInitialLogs()}
                        </div>
                        <div class="status-panel">
                            <div class="status-item">
                                <span id="cpu-usage">CPU: 15%</span>
                            </div>
                            <div class="status-item">
                                <span id="memory-usage">Memory: 32%</span>
                            </div>
                            <div class="status-item">
                                <span id="connections">Clients: 2</span>
                            </div>
                        </div>
                    </div>
                    <footer>&copy; 2024 Harmony Haven</footer>
                    <script>
                        const logsContainer = document.getElementById('logs');
                        const logLines = [
                            "Initializing Harmony Haven Server...",
                            "Connecting to database...",
                            "Database connected successfully.",
                            "WebSocket service started on port 8080.",
                            "Listening for client connections...",
                            "Client connected: 192.168.1.100",
                            "Received message: { action: 'status' }",
                            "Response sent: { status: 'OK' }",
                            "Routine maintenance complete.",
                            "No issues detected.",
                            "Server running smoothly."
                        ];

                        const cpuUsage = document.getElementById('cpu-usage');
                        const memoryUsage = document.getElementById('memory-usage');
                        const connections = document.getElementById('connections');

                        // Function to update logs
                        setInterval(() => {
                            const logLine = document.createElement('div');
                            logLine.className = 'log';
                            logLine.textContent = logLines[Math.floor(Math.random() * logLines.length)];
                            logsContainer.appendChild(logLine);

                            if (logsContainer.children.length > 10) {
                                logsContainer.removeChild(logsContainer.children[0]);
                            }
                        }, 1500);

                        // Function to update server status
                        setInterval(() => {
                            cpuUsage.textContent = "CPU: " + (Math.random() * 100).toFixed(2) + "%";
                            memoryUsage.textContent = "Memory: " + (Math.random() * 100).toFixed(2) + "%";
                            connections.textContent = "Clients: " + Math.floor(Math.random() * 20);
                        }, 2000);
                    </script>
                </body>
                </html>
                """.trimIndent(),
                ContentType.Text.Html
            )
        }

        // Configure versioned API routes
        versionedApiRoutes()
    }

    // Helper function to generate a flowing "code-like" background


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
        getQuotes()
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
