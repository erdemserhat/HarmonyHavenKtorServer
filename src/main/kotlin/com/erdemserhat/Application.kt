package com.erdemserhat

import com.erdemserhat.data.cache.sendWelcomeNotification
import com.erdemserhat.data.database.nosql.MongoDatabaseConfig.mongoDatabase
import com.erdemserhat.data.database.nosql.enneagram_chart.EnneagramChartCollection
import com.erdemserhat.data.database.nosql.enneagram_extra_type_description.EnneagramExtraTypeDescriptionCategory
import com.erdemserhat.data.database.nosql.enneagram_extra_type_description.EnneagramExtraTypeDescriptionCollection
import com.erdemserhat.data.database.nosql.enneagram_famous_people.EnneagramFamousPeopleCollection
import com.erdemserhat.data.database.nosql.enneagram_famous_people.EnneagramFamousPeopleRepositoryImpl
import com.erdemserhat.data.database.nosql.enneagram_famous_people.EnneagramType
import com.erdemserhat.data.database.nosql.enneagram_question.EnneagramQuestionCategory
import com.erdemserhat.data.database.nosql.enneagram_question.EnneagramQuestionRepositoryImpl
import com.erdemserhat.data.database.nosql.enneagram_type_descriptions.EnneagramTypeDescriptionCategory
import com.erdemserhat.data.database.nosql.enneagram_type_descriptions.EnneagramTypeDescriptionCollection
import com.erdemserhat.data.database.nosql.moods.moods.MoodsCollection
import com.erdemserhat.data.database.nosql.notification_preferences.*
import com.erdemserhat.data.database.sql.enneagram.enneagram_famous_people.DBEnneagramFamousPeopleTable.enneagramType
import com.erdemserhat.data.database.sql.enneagram.enneagram_questions.DBEnneagramQuestionTable.personalityNumber
import com.erdemserhat.data.database.sql.enneagram.enneagram_questions.toEnneagramQuestionCollection
import com.erdemserhat.dto.requests.FcmNotification
import com.erdemserhat.dto.requests.SendNotificationSpecific
import com.erdemserhat.dto.requests.toFcmMessage
import com.erdemserhat.plugins.*
import com.erdemserhat.service.NotificationAI
import com.erdemserhat.service.configurations.*
import com.erdemserhat.service.di.AuthenticationModule.tokenConfigSecurity
import com.erdemserhat.service.di.DatabaseModule
import com.erdemserhat.service.di.DatabaseModule.moodsRepository
import com.erdemserhat.service.di.DatabaseModule.notificationPreferencesRepository
import com.erdemserhat.service.di.DatabaseModule.userMoodsRepository
import com.erdemserhat.service.di.DatabaseModule.userRepository
import com.erdemserhat.service.di.EnneagramRepositoryModule
import com.erdemserhat.service.di.EnneagramRepositoryModule.enneagramChartRepository
import com.erdemserhat.service.di.EnneagramRepositoryModule.enneagramExtraTypeDescriptionRepository
import com.erdemserhat.service.di.EnneagramRepositoryModule.enneagramFamousPeopleRepository
import com.erdemserhat.service.enneagram.EnneagramTempRepository
import com.erdemserhat.service.enneagram.toCollection
import com.erdemserhat.service.notification.startNotificationScheduler
import com.erdemserhat.service.openai.OpenAiNotificationService
import com.erdemserhat.service.openai.configureOpenAiCredentials
import com.erdemserhat.service.sendAIBasedMessage
import com.google.firebase.messaging.FirebaseMessaging
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import io.ktor.server.application.*
import io.ktor.server.netty.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import kotlinx.serialization.json.Json
import org.bson.types.ObjectId
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit

fun main(args: Array<String>) {
    EngineMain.main(args)
}

data class Person(val name: String, val age: Int)

fun Application.module() {

    configureCORS()
    configureSerialization()
    configureTemplating()
    configureOpenAiCredentials()
    configureSMTP()
    configureFTP()
    configureRemoteDatabase()
    configureRemoteMongoDatabase()
    configureFirebase()
    configureTokenConfig()
    configureSecurity(tokenConfigSecurity)
    configureRouting()
    startNotificationScheduler(scope = CoroutineScope(Dispatchers.Default))


}


