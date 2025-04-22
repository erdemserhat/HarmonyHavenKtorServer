package com.erdemserhat

import com.erdemserhat.data.database.enneagram.enneagram_answers.EnneagramAnswersDto
import com.erdemserhat.plugins.*
import com.erdemserhat.service.EnneagramService
import com.erdemserhat.service.configurations.*
import com.erdemserhat.service.di.AuthenticationModule.tokenConfigSecurity
import com.erdemserhat.service.openai.configureOpenAiCredentials
import io.ktor.server.application.*
import io.ktor.server.netty.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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

    GlobalScope.launch {
        val answers = listOf(
            EnneagramAnswersDto(questionId = 91, score = 1),
            EnneagramAnswersDto(questionId = 145, score = 2),
            EnneagramAnswersDto(questionId = 153, score = 3),
            EnneagramAnswersDto(questionId = 191, score = 1),
            EnneagramAnswersDto(questionId = 230, score = 2),
            EnneagramAnswersDto(questionId = 265, score = 3),
            EnneagramAnswersDto(questionId = 297, score = 1),
            EnneagramAnswersDto(questionId = 306, score = 2),
            EnneagramAnswersDto(questionId = 351, score = 3),
            EnneagramAnswersDto(questionId = 351, score = 3)






            )

        val a = EnneagramService().evaluateTestResult(answers)


        println(a)
    }

}

