package com.erdemserhat.plugins

import freemarker.cache.ClassTemplateLoader
import freemarker.core.HTMLOutputFormat
import io.ktor.server.application.*
import io.ktor.server.freemarker.*

/**
 * Configures the FreeMarker templating engine.
 * Sets up FreeMarker template loading and HTML output format.
 */
fun Application.configureTemplating() {
    install(FreeMarker) {
        // Set the template loader to load templates from the "templates" directory
        templateLoader = ClassTemplateLoader(this@configureTemplating::class.java.classLoader, "templates")
        // Set the output format to HTML
        outputFormat = HTMLOutputFormat.INSTANCE
    }
}
