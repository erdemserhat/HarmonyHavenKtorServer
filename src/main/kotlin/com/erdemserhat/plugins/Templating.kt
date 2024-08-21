package com.erdemserhat.plugins

import com.erdemserhat.util.configuration
import freemarker.cache.ClassTemplateLoader
import freemarker.core.HTMLOutputFormat
import freemarker.template.Configuration
import freemarker.template.DefaultObjectWrapperBuilder
import freemarker.template.TemplateExceptionHandler
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import java.nio.charset.StandardCharsets

/**
 * Configures the FreeMarker templating engine.
 * Sets up FreeMarker template loading and HTML output format.
 */
fun Application.configureTemplating() {
    install(FreeMarker) {
        // Set the template loader to load templates from the "templates" directory
        templateLoader = ClassTemplateLoader(this@configureTemplating::class.java.classLoader, "templates")

        // Set the FreeMarker configuration
        configuration.apply {
            // Ensure UTF-8 encoding for templates and output
            defaultEncoding = StandardCharsets.UTF_8.name()
            outputFormat = HTMLOutputFormat.INSTANCE
            // Handle template exceptions
            templateExceptionHandler = TemplateExceptionHandler.RETHROW_HANDLER
            // Set the object wrapper for FreeMarker
            objectWrapper = DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_30).build()
        }
    }
}