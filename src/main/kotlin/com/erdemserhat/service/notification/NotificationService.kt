package com.erdemserhat.service.notification

import com.erdemserhat.data.database.nosql.notification_preferences.*
import com.erdemserhat.dto.requests.FcmNotification
import com.erdemserhat.dto.requests.SendNotificationSpecific
import com.erdemserhat.dto.requests.toFcmMessage
import com.erdemserhat.service.di.DatabaseModule.notificationPreferencesRepository
import com.erdemserhat.service.di.DatabaseModule.userRepository
import com.erdemserhat.service.openai.OpenAiNotificationService
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit


suspend fun checkAndTriggerNotifications() {
    println("checked")
    val now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
    val today = now.dayOfWeek
    val preferences = notificationPreferencesRepository.getAllNotificationPreferences()


    val semaphore = Semaphore(permits = 10)

    coroutineScope {
        for (preference in preferences) {
            val isToday = today in preference.daysOfWeek
            val isTimeMatched = now.toLocalTime() == preference.preferredTime.truncatedTo(ChronoUnit.MINUTES)
            val isAlreadySent = preference.lastSentAt?.truncatedTo(ChronoUnit.MINUTES) == now

            if (isToday && isTimeMatched && !isAlreadySent) {
                launch {
                    semaphore.withPermit {
                        triggerNotification(preference)
                        notificationPreferencesRepository.updateLastSentAt(preference.id, now)
                    }
                }
            }
        }
    }
}


fun triggerNotification(preference: NotificationPreferencesCollection) {
    println("Triggering notification")
    CoroutineScope(Dispatchers.Default).launch {
        val user = userRepository.getUserById(preference.userId)
        val userName = user!!.name
        val email = user.email
        var prompt =  ""

        when (preference.type) {
            NotificationType.REMINDER -> {

                when (preference.definedType) {
                    NotificationDefinedType.DEFAULT -> {
                        val defaultRemainderSubject = preference.predefinedReminderSubject
                        val defaultRemainderPrompt = when (defaultRemainderSubject) {
                            PredefinedReminderSubject.EXERCISE -> {
                                "spor yapmayı hatırlat"
                            }

                            PredefinedReminderSubject.WATER_DRINK -> {
                                "su içmemi hatırlat"
                            }

                            PredefinedReminderSubject.SLEEP_TIME -> {
                                "uyku vaktinin geldiğini hatırlat"
                            }

                            else -> {
                                throw IllegalArgumentException("Unknown preference type ${preference.predefinedReminderSubject}")
                            }

                        }

                        prompt = defaultRemainderPrompt

                    }

                    NotificationDefinedType.CUSTOM -> {
                        val customRemainderPrompt = preference.customSubject
                        prompt = customRemainderPrompt!!


                    }

                }

            }

            NotificationType.MESSAGE -> {
                when (preference.definedType) {
                    NotificationDefinedType.DEFAULT -> {
                        val defaultMessageSubject = preference.predefinedMessageSubject

                        val defaultMessagePrompt = when (defaultMessageSubject) {
                            PredefinedMessageSubject.MOTIVATION -> {
                                "beni motive et "
                            }

                            PredefinedMessageSubject.GOOD_MORNING -> {
                                "günaydın mesajı"
                            }

                            PredefinedMessageSubject.GOOD_EVENING -> {
                                "iyi geceler mesajı yaz"
                            }

                            else -> {
                                throw IllegalArgumentException("Unknown preference type ${preference.predefinedMessageSubject}")
                            }

                        }

                        prompt = defaultMessagePrompt

                    }

                    NotificationDefinedType.CUSTOM -> {
                        //CUSTOM MESSAGES
                        val customRemainderPrompt = preference.customSubject
                        println("worked")
                        prompt = customRemainderPrompt!!

                    }

                }


            }


        }

        val message = OpenAiNotificationService.generateNotification(
            userId = user.id,
            userName = userName,
            content = prompt,
            notificationType = preference.type,
        )

        val specificNotification = SendNotificationSpecific(
            email = email,
            notification = FcmNotification(
                title = "Harmony Haven",
                body = message,
            )
        )
        FirebaseMessaging.getInstance().send(specificNotification.toFcmMessage())


    }

}

fun startNotificationScheduler(scope: CoroutineScope) {
    scope.launch {
        while (isActive) {
            checkAndTriggerNotifications()
            delay(30_000)
        }
    }
}



