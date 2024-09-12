package com.erdemserhat.service

import kotlinx.coroutines.*
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

class MessageScheduler(
    val onTime: suspend () -> Unit,
    val cycleDay: Long,
    val performingHour: Int,
    val performingMinute: Int=0,
) {
    private val scope = CoroutineScope(Dispatchers.IO)

    suspend fun start() {
        scope.launch {
            while (true) {
                val now = LocalDateTime.now(ZoneId.of("Europe/Istanbul"))
                val nextRun = calculateNextRunTime(now)
                val delay = ChronoUnit.MILLIS.between(now, nextRun)

                // Delay should only be applied if it's positive.
                if (delay > 0) {
                    delay(delay)
                }
                onTime() // Trigger the scheduled task
            }
        }
    }

    private fun calculateNextRunTime(now: LocalDateTime): LocalDateTime {
        // Calculate the next run time for the given performing hour today
        var nextRun = now.withHour(performingHour).withMinute(performingMinute).withSecond(0).withNano(0)

        // If the current time is after the scheduled time, move to the next cycle day
        if (now.isAfter(nextRun)) {
            nextRun = nextRun.plusDays(cycleDay)
        }
        return nextRun
    }
}
