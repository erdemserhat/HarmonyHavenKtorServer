package com.erdemserhat.service.configurations

import com.erdemserhat.service.MessageScheduler
import com.erdemserhat.service.NotificationAICategories
import com.erdemserhat.service.sendAIBasedMessage
import kotlinx.coroutines.*

suspend fun configureNotificationScheduler() {
    coroutineScope {
        launch {
            println("Special Notifications Activated")
            val agaMessageScheduler = MessageScheduler(
                onTime = {
                    sendAIBasedMessage(
                        NotificationAICategories.islamic(),
                        sendSpecificByEmailList = listOf(
                            "editkurama@gmail.com",
                            "suatahmet2323@gmail.com",
                            "serhaterdem961@gmail.com"
                        )
                    )

                },
                cycleDay = 1,
                performingHour = 20
            )
            agaMessageScheduler.start()
        }

        launch {
            println("Good Morning Notifications Activated")
            val goodMorningMessageScheduler = MessageScheduler(
                onTime = {
                    sendAIBasedMessage(
                        NotificationAICategories.goodMorning()
                    )

                },
                cycleDay = 3,
                performingHour = 10,
                performingMinute = 15
            )
            goodMorningMessageScheduler.start()
        }


        launch {
            println("Good Night Notifications Activated")
            val goodNightMessageScheduler = MessageScheduler(
                onTime = {
                    sendAIBasedMessage(
                        NotificationAICategories.goodNight()
                    )

                },
                cycleDay = 2,
                performingHour = 22,
                performingMinute = 20
            )
            goodNightMessageScheduler.start()
        }


        launch {
            println("Random Notifications Activated")
            val randomMessageScheduler = MessageScheduler(
                onTime = {
                    sendAIBasedMessage(
                        NotificationAICategories.list.random()
                    )

                },
                cycleDay = 1,
                performingHour = 16,
                performingMinute = 45
            )
            randomMessageScheduler.start()
        }



    }
}

