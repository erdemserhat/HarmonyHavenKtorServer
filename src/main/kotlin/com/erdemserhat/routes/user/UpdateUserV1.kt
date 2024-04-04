package com.erdemserhat.routes.user

import com.erdemserhat.service.di.DatabaseModule.userRepository
import com.erdemserhat.service.validation.UserInformationValidatorService
import com.erdemserhat.service.validation.validateIfEmailChanged
import com.erdemserhat.models.UserInformationSchema
import com.erdemserhat.dto.responses.RequestResult
import com.erdemserhat.models.toUser
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.updateUserV1() {
    authenticate {
        patch("/user/update") {
            val principal = call.principal<JWTPrincipal>()
            val email = principal?.payload?.getClaim("email")?.asString()
            val updatedUserData = call.receive<UserInformationSchema>()
            try {

                val validator = UserInformationValidatorService(updatedUserData)
                val validatorResult = validator.validateForm(byCheckingExistenceOfUser = false)

                if (!validatorResult.isValid) {
                    call.respond(
                        status = HttpStatusCode.UnprocessableEntity,
                        message = validatorResult.errorMessage
                    )
                    return@patch
                }

                if (email == null) {
                    call.respond(
                        status = HttpStatusCode.UnprocessableEntity,
                        message = "There is a problem with your session"
                    )
                    return@patch
                }

                val emailValidationResult = validateIfEmailChanged(email, updatedUserData.email)

                if (!emailValidationResult.isValid) {
                    call.respond(
                        status = HttpStatusCode.UnprocessableEntity,
                        message = emailValidationResult.errorMessage
                    )
                    return@patch

                }


                userRepository.updateUserByEmail(email, updatedUserData.toUser())

                call.respond(
                    status = HttpStatusCode.OK,
                    message = "Information is updated..."
                )



            } catch (e: Exception) {
                call.respond(RequestResult(true, e.message.toString()))

            }


        }

    }


}

