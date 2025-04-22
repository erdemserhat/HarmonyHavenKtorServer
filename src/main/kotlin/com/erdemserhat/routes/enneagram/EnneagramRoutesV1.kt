package com.erdemserhat.routes.enneagram

import com.erdemserhat.data.database.enneagram.enneagram_answers.EnneagramAnswersDto
import com.erdemserhat.service.EnneagramService
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.launch

fun Route.enneagramRoutesV1() {
    val enneagramService = EnneagramService()
    authenticate {
        get("/enneagram/questions") {
            call.respond(status = HttpStatusCode.OK, message = enneagramService.getQuestions())
        }

        post("/enneagram/answers") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal!!.payload.claims["id"]!!.asInt()
            val answersDto = call.receive<List<EnneagramAnswersDto>>()
            val results = enneagramService.evaluateTestResult(answersDto)
            call.application.launch {
                enneagramService.saveTestResult(results,answersDto,userId)
            }

            call.respond(status = HttpStatusCode.OK, message = results)


        }



    }
}