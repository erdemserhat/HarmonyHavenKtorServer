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
import com.erdemserhat.data.database.sql.enneagram.enneagram_questions.DBEnneagramQuestionTable.personalityNumber
import com.erdemserhat.data.database.sql.enneagram.enneagram_questions.toEnneagramQuestionCollection
import com.erdemserhat.plugins.*
import com.erdemserhat.service.configurations.*
import com.erdemserhat.service.di.AuthenticationModule.tokenConfigSecurity
import com.erdemserhat.service.di.DatabaseModule
import com.erdemserhat.service.di.EnneagramRepositoryModule
import com.erdemserhat.service.di.EnneagramRepositoryModule.enneagramExtraTypeDescriptionRepository
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


    GlobalScope.launch {
        val database = mongoDatabase
        /*

        val descRepo = EnneagramRepositoryModule.enneagramTypeDescriptionRepository


        val types = listOf(
            "1w2" to EnneagramType(1, 2),
            "1w9" to EnneagramType(1, 9),
            "2w1" to EnneagramType(2, 1),
            "2w3" to EnneagramType(2, 3),
            "3w2" to EnneagramType(3, 2),
            "3w4" to EnneagramType(3, 4),
            "4w3" to EnneagramType(4, 3),
            "4w5" to EnneagramType(4, 5),
            "5w4" to EnneagramType(5, 4),
            "5w6" to EnneagramType(5, 6),
            "6w5" to EnneagramType(6, 5),
            "6w7" to EnneagramType(6, 7),
            "7w6" to EnneagramType(7, 6),
            "7w8" to EnneagramType(7, 8),
            "8w9" to EnneagramType(8, 9),
            "8w7" to EnneagramType(8, 7),
            "9w8" to EnneagramType(9, 8),
            "9w1" to EnneagramType(9, 1)
        )

        types.forEach {type->
            val desc = EnneagramTempRepository.getTypeDescription(
                generalType = type.first,
                enneagramBasedDominantWingType = type.second.wingType

            )

            val collection = EnneagramTypeDescriptionCollection(
                description= desc,
                enneagramTypeDescriptionCategory = EnneagramTypeDescriptionCategory.BASIC,
                enneagramType = type.second

            )

            descRepo.addEnneagramTypeDescription(collection)


        }

         */









    }



}

