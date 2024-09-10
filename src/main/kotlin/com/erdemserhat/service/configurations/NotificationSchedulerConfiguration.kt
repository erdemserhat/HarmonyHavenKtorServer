package com.erdemserhat.service.configurations

import MessageScheduler
import com.erdemserhat.service.NotificationAICategories
import com.erdemserhat.service.sendAIBasedMessage
import io.ktor.server.application.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

fun Application.configureNotificationScheduler() {
    val coroutineScope = CoroutineScope(Dispatchers.Default)
    coroutineScope.launch {

        ///////////////////////////// S P E C I A L ///////////////////////////////////////////////////////

        val deferredSpecialNotifications = async {
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
        deferredSpecialNotifications.await()

        ///////////////////////////// M O R N I N G //////////////////////////////
        val deferredGoodMorningNotifications = async {
            println("Good Morning Notifications Activated")
            val goodMorningMessageScheduler = MessageScheduler(
                onTime = {
                    sendAIBasedMessage(
                        NotificationAICategories.goodMorning()
                    )

                },
                cycleDay = 2,
                performingHour = 10
            )
            goodMorningMessageScheduler.start()
        }
        deferredGoodMorningNotifications.await()

        ///////////////////////////// N I G H T //////////////////////////////

        val deferredGoodNightNotifications = async {
            println("Good Night Notifications Activated")
            val goodNightMessageScheduler = MessageScheduler(
                onTime = {
                    sendAIBasedMessage(
                        NotificationAICategories.goodNight()
                    )

                },
                cycleDay = 3,
                performingHour = 22
            )
            goodNightMessageScheduler.start()
        }
        deferredGoodNightNotifications.await()

        /////////////////////// MOTIVATION MESSAGE ////////////////////////

        val deferredMotivationNotifications = async {
            println("Motivation Notifications Activated")
            val motivationMessageScheduler = MessageScheduler(
                onTime = {
                    sendAIBasedMessage(
                        NotificationAICategories.motivation()
                    )

                },
                cycleDay = 2,
                performingHour = 16
            )
            motivationMessageScheduler.start()
        }
        deferredMotivationNotifications.await()

        /////////////////////// RANDOM MESSAGE ////////////////////////

        val deferredRandomNotifications = async {
            println("Random Notifications Activated")
            val randomMessageScheduler = MessageScheduler(
                onTime = {
                    sendAIBasedMessage(
                        NotificationAICategories.list.random()
                    )

                },
                cycleDay = 3,
                performingHour = 18
            )
            randomMessageScheduler.start()
        }
        deferredRandomNotifications.await()


    }


}