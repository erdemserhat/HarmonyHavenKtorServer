package com.erdemserhat.routes.user

import com.erdemserhat.di.DatabaseModule.userRepository
import com.erdemserhat.domain.validation.UserInformationValidatorService
import com.erdemserhat.domain.validation.validateIfEmailChanged
import com.erdemserhat.models.UserInformationSchema
import com.erdemserhat.models.rest.server.RequestResult
import com.erdemserhat.models.rest.client.UserUpdateModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.updateUser() {

    /*


    authenticate{
        patch("/user/update") {
            val principal = call.principal<JWTPrincipal>()
            val email = principal?.payload?.getClaim("email")?.asString()
            val updatedUserData = call.receive<UserInformationSchema>()
            try {

                val validator = UserInformationValidatorService(updatedUserData)
                val validatorResult = validator.validateForm()

                if(!validatorResult.isValid){
                    call.respond(
                        status = HttpStatusCode.UnprocessableEntity,
                        message = validatorResult.errorMessage
                    )
                    return@patch
                }



                validateIfEmailChanged(userLoginData, updateData)
                if (userRepository.controlUserExistenceByAuth(userLoginData)) {
                    userRepository.updateUserByLoginInformation(userLoginData, updateData)
                    call.respond(RequestResult(true, "User Updated"))
                } else {
                    call.respond(RequestResult(true, "There is no user like that"))
                }


            } catch (e: Exception) {
                call.respond(RequestResult(true, e.message.toString()))

            }


        }

    }

    */




}

