package com.erdemserhat.routes.user

import com.erdemserhat.dto.requests.UserAuthenticationRequest
import com.erdemserhat.service.di.DatabaseModule.userRepository
import com.erdemserhat.service.validation.validateIfEmailChanged
import com.erdemserhat.models.UserInformationSchema
import com.erdemserhat.dto.responses.RequestResult
import com.erdemserhat.dto.responses.UpdateNameDto
import com.erdemserhat.dto.responses.UpdatePasswordDto
import com.erdemserhat.models.toUser
import com.erdemserhat.service.authentication.UserAuthenticationCredentialsValidatorService
import com.erdemserhat.service.di.DatabaseModule
import com.erdemserhat.service.security.hashPassword
import com.erdemserhat.service.validation.UserAuthenticationInputValidatorService
import com.erdemserhat.service.validation.UserInformationValidatorService
import com.erdemserhat.service.validation.ValidationResult
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

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    authenticate {
        patch("/user/update-password") {

            val principal = call.principal<JWTPrincipal>()
            val email = principal?.payload?.getClaim("email")?.asString()
            val user = userRepository.getUserByEmailInformation(email!!)
            val newPasswordData = call.receive<UpdatePasswordDto>()
            val credentialValidator = UserAuthenticationCredentialsValidatorService(UserAuthenticationRequest(
                email = email,
                password = newPasswordData.currentPassword
            ))

            val newPasswordValidationResult = UserInformationValidatorService(
                user = UserInformationSchema(password = newPasswordData.newPassword)
            ).validateForm(shouldOnlyValidatePassword = true)

            println(newPasswordData.newPassword)
            println(newPasswordValidationResult.toString())

            val validationResult = credentialValidator.verifyUser()
            if(!validationResult.isValid){
                call.respond(
                    status = HttpStatusCode.Unauthorized,
                    message = ValidationResult(
                        isValid = false,
                        errorMessage = validationResult.errorMessage,
                        errorCode = 0

                    )
                )
                return@patch


            }

            if(!newPasswordValidationResult.isValid){
                call.respond(
                    status = HttpStatusCode.Unauthorized,
                    message = newPasswordValidationResult

                    )

                return@patch


            }

         //   val hashedPassword = makeEncryptionRequest(
         //   EncryptionToFarawayServerModel(
           //     encryptionData = EncryptionDataDto(
            //        sensitiveData = newPasswordData.newPassword,
            //        userUUID = user!!.uuid
           //     )
          //  )
       // )

            val hashedPassword = hashPassword(newPasswordData.newPassword)
            println(hashedPassword)

            userRepository.updateUserPasswordByEmail(email,hashedPassword)

            call.respond(
                status = HttpStatusCode.OK,
                message = ValidationResult()
            )








        }




        patch("user/update-name") {
            val principal = call.principal<JWTPrincipal>()
            val email = principal?.payload?.getClaim("email")?.asString()
            val user = userRepository.getUserByEmailInformation(email!!)
            val newName = call.receive<UpdateNameDto>().name
            userRepository.updateUserByEmail(email, newUser = user!!.copy(name = newName))

            call.respond(
                status = HttpStatusCode.OK,
                message = ValidationResult()
            )







        }
















    }


}

