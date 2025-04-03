package com.erdemserhat.service.configurations

import com.erdemserhat.service.MessageScheduler
import com.erdemserhat.service.sendAIBasedMessage
import kotlinx.coroutines.*

suspend fun configureNotificationScheduler() {
    coroutineScope {
        /*


        // Günlük Motivasyon Bildirimleri
        launch {
            println("Daily Motivation Notifications Activated")
            val dailyMotivationScheduler = MessageScheduler(
                onTime = {
                    sendAIBasedMessage(
                        NotificationAICategories.motivation(),
                        sendSpecificByEmailList = listOf(
                            "editkurama@gmail.com",
                            "suatahmet2323@gmail.com",
                            "serhaterdem961@gmail.com"
                        )
                    )
                },
                cycleDay = 1,
                performingHour = 8,
                performingMinute = 30
            )
            dailyMotivationScheduler.start()
        }

        // Kariyer Motivasyon Bildirimleri
        launch {
            println("Career Motivation Notifications Activated")
            val careerMotivationScheduler = MessageScheduler(
                onTime = {
                    sendAIBasedMessage(
                        NotificationAICategories.motivation()
                    )
                },
                cycleDay = 2,
                performingHour = 10,
                performingMinute = 15
            )
            careerMotivationScheduler.start()
        }

        // Kişisel Gelişim Motivasyon Bildirimleri
        launch {
            println("Personal Growth Motivation Notifications Activated")
            val personalGrowthScheduler = MessageScheduler(
                onTime = {
                    sendAIBasedMessage(
                        NotificationAICategories.motivation()
                    )
                },
                cycleDay = 2,
                performingHour = 14,
                performingMinute = 30
            )
            personalGrowthScheduler.start()
        }

        // Sağlık Motivasyon Bildirimleri
        launch {
            println("Health Motivation Notifications Activated")
            val healthMotivationScheduler = MessageScheduler(
                onTime = {
                    sendAIBasedMessage(
                        NotificationAICategories.motivation()
                    )
                },
                cycleDay = 3,
                performingHour = 16,
                performingMinute = 20
            )
            healthMotivationScheduler.start()
        }

        // Başarı Motivasyon Bildirimleri
        launch {
            println("Success Motivation Notifications Activated")
            val successMotivationScheduler = MessageScheduler(
                onTime = {
                    sendAIBasedMessage(
                        NotificationAICategories.motivation()
                    )
                },
                cycleDay = 1,
                performingHour = 20,
                performingMinute = 45
            )
            successMotivationScheduler.start()
        }

        // Rastgele Motivasyon Bildirimleri
        launch {
            println("Random Motivation Notifications Activated")
            val randomMotivationScheduler = MessageScheduler(
                onTime = {
                    sendAIBasedMessage(
                        NotificationAICategories.list.random()
                    )
                },
                cycleDay = 1,
                performingHour = 12,
                performingMinute = 30
            )
            randomMotivationScheduler.start()
        }
         */



    }


}

