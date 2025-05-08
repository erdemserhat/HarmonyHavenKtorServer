package com.erdemserhat.routes.enneagram

import com.erdemserhat.data.database.nosql.enneagram_question.EnneagramQuestionCategory
import com.erdemserhat.data.database.sql.enneagram.enneagram_answers.EnneagramAnswerDto
import com.erdemserhat.service.enneagram.CheckingTestResultDto
import com.erdemserhat.service.enneagram.EnneagramService
import com.erdemserhat.service.enneagram.EnneagramTempRepository
import com.erdemserhat.service.enneagram.EvaluationMode
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.enneagramRoutesV1() {
    val enneagramService = EnneagramService()
    authenticate {
        get("/enneagram/questions") {
            call.respond(status = HttpStatusCode.OK, message = enneagramService.getQuestions(
                questionCategory = EnneagramQuestionCategory.BASIC
            ).shuffled())
        }
        /*
        get("/enneagram/questions/{mode}") {
            val mode = call.parameters["mode"]
            val question:List<EnneagramQuestionDto>
            //1->basic
            //2->standard
            //3->professional

            when(mode) {
                "basic"-> EnneagramTempRepository.BASIC_QUESTIONS
                "standard"-> enneagramService.getQuestions()
                "professional"-> enneagramService.getQuestions()
                else -> call.respond(HttpStatusCode.BadRequest, "basic, standard, professional-> '$mode'")
            }





            call.respond(status = HttpStatusCode.OK, message = enneagramService.getQuestions())
        }

         */

        post("/enneagram/answers") {
            try {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal!!.payload.claims["id"]!!.asInt()
                val answersDto = call.receive<List<EnneagramAnswerDto>>()


                val results = enneagramService.evaluateTestResult(
                    mode = EvaluationMode.BASIC,
                    answers = answersDto,
                    userId = userId

                )
                // call.application.launch {
                //   EnneagramTempRepository.SAVED_QUESTIONS[userId] = results
                // }
                call.respond(status = HttpStatusCode.OK, message = results)

            }catch (e:Exception){
                println(e)
            }


        }

        get("/enneagram/check-test-status") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal!!.payload.claims["id"]!!.asInt()

            val result = enneagramService.checkResults(userId)

            val isTestTakenBefore = result !=null

            val detailedResult = CheckingTestResultDto(
                isTestTakenBefore = isTestTakenBefore,
                detailedResult = result
            )

            call.respond(status = HttpStatusCode.OK, message = detailedResult)



        }



    }
}

