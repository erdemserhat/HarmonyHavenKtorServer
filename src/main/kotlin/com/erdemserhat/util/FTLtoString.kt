package com.erdemserhat.util

import freemarker.cache.ClassTemplateLoader
import freemarker.template.Configuration
import io.ktor.server.freemarker.*
import java.io.StringWriter

// FreeMarker konfigürasyonunu oluştur
val configuration = Configuration(Configuration.VERSION_2_3_31).apply {
    templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
}

// FreeMarkerContent nesnesini bir dizeye dönüştürme
fun convertToString(content: FreeMarkerContent): String {
    val template = configuration.getTemplate(content.template)
    val output = StringWriter()
    template.process(content.model, output)
    return output.toString()
}