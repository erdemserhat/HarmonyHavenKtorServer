package com.erdemserhat.service.configurations.rate_limiting

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.application.ApplicationCallPipeline.ApplicationPhase.Plugins
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

fun Application.configureRateLimiting2() {
    // Her IP için istek zamanlarını tutan bir yapı
    val requestCounts = ConcurrentHashMap<String, MutableList<Long>>()

    val limit = 150 // Saatlik maksimum istek sayısı
    val timeWindowMillis = TimeUnit.HOURS.toMillis(1)

    intercept(Plugins) {
        val ip = call.request.origin.remoteHost
        val currentTime = System.currentTimeMillis()

        // IP adresine ait zaman damgalarını al, yoksa oluştur
        val timestamps = requestCounts.computeIfAbsent(ip) { mutableListOf() }

        // Eski zaman damgalarını temizle
        timestamps.removeIf { it < currentTime - timeWindowMillis }

        if (timestamps.size >= limit) {
            // Limit aşıldıysa yanıt ver ve pipeline'ı bitir
            call.respond(HttpStatusCode.TooManyRequests, "Rate limit exceeded. Try again later.")
            return@intercept finish()
        }

        // Yeni zaman damgasını listeye ekle
        timestamps.add(currentTime)
        proceed() // Pipeline'a devam et
    }
}