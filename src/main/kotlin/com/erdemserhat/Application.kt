package com.erdemserhat

import com.erdemserhat.data.database.enneagram.enneagram_answers.DBEnneagramAnswersEntity
import com.erdemserhat.data.database.enneagram.enneagram_answers.EnneagramAnswersDaoImpl
import com.erdemserhat.data.database.enneagram.enneagram_answers.EnneagramAnswersDto
import com.erdemserhat.data.database.enneagram.enneagram_famous_people.EnneagramFamousPeopleDao
import com.erdemserhat.data.database.enneagram.enneagram_famous_people.EnneagramFamousPeopleDaoImpl
import com.erdemserhat.data.database.enneagram.enneagram_questions.EnneagramQuestionDaoImpl
import com.erdemserhat.data.database.enneagram.enneagram_test_results.EnneagramTestResultDaoImpl
import com.erdemserhat.data.database.enneagram.enneagram_test_results.EnneagramTestResultsDto
import com.erdemserhat.plugins.*
import com.erdemserhat.service.configurations.*
import com.erdemserhat.service.di.AuthenticationModule.tokenConfigSecurity
import com.erdemserhat.service.openai.configureOpenAiCredentials
import io.ktor.server.application.*
import io.ktor.server.netty.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureCORS()
    configureSerialization()
    configureTemplating()
    configureOpenAiCredentials()
    configureSMTP()
    configureFTP()
    configureRemoteDatabase()
    configureFirebase()
    configureTokenConfig()
    configureSecurity(tokenConfigSecurity)
    configureRouting()

}

