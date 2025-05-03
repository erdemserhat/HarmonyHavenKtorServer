package com.erdemserhat

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
import com.erdemserhat.data.database.sql.enneagram.enneagram_famous_people.DBEnneagramFamousPeopleTable.enneagramType
import com.erdemserhat.data.database.sql.enneagram.enneagram_questions.DBEnneagramQuestionTable.personalityNumber
import com.erdemserhat.data.database.sql.enneagram.enneagram_questions.toEnneagramQuestionCollection
import com.erdemserhat.plugins.*
import com.erdemserhat.service.configurations.*
import com.erdemserhat.service.di.AuthenticationModule.tokenConfigSecurity
import com.erdemserhat.service.di.DatabaseModule
import com.erdemserhat.service.di.EnneagramRepositoryModule
import com.erdemserhat.service.di.EnneagramRepositoryModule.enneagramChartRepository
import com.erdemserhat.service.di.EnneagramRepositoryModule.enneagramExtraTypeDescriptionRepository
import com.erdemserhat.service.di.EnneagramRepositoryModule.enneagramFamousPeopleRepository
import com.erdemserhat.service.enneagram.EnneagramTempRepository
import com.erdemserhat.service.enneagram.toCollection
import com.erdemserhat.service.openai.configureOpenAiCredentials
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import io.ktor.server.application.*
import io.ktor.server.netty.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import org.bson.types.ObjectId

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




}

