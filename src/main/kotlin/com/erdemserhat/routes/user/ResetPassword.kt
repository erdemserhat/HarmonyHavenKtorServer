package com.erdemserhat.routes.user

import com.erdemserhat.di.DatabaseModule
import com.erdemserhat.di.DatabaseModule.userRepository
import com.erdemserhat.domain.password.PasswordResetRequests
import com.erdemserhat.domain.password.PasswordResetRequests.getUUIDOfResetRequest
import com.erdemserhat.domain.password.PasswordResetRequests.provideEmailOfRequesterIfExist
import com.erdemserhat.domain.validation.isUUIDFormat
import com.erdemserhat.domain.validation.validateEmailFormat
import com.erdemserhat.domain.validation.validatePasswordFormat
import com.erdemserhat.models.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.erdemserhat.di.DatabaseModule.passwordResetService as RESET_SERVICE

/**
 * This function handles the user's reset password request.
 * There are some conditions which user provide ;
 * 1 -> User has to enter valid code within maximum three attempts
 * 2 -> User has to enter valid code within 20 min
 * 3 ->User has to define valid password for security
 */

fun Route.resetPassword() {
    route("/user/forgot-password/mailer") {
        post {
            try {
                val resetPasswordRequest = call.receive<ForgotPasswordMailerModel>()
                validateEmailFormat(resetPasswordRequest.email)

                if (DatabaseModule.userRepository.controlUserExistenceByEmail(resetPasswordRequest.email)) {
                    RESET_SERVICE.createRequest(resetPasswordRequest.email)
                    call.respond(RequestResult(true, "Mail sent"))

                } else {
                    call.respond(RequestResult(false, "User not found"))
                }

            } catch (e: Exception) {
                call.respond(RequestResult(false, e.message.toString()))
            }

        }


    }

    route("/user/forgot-password/auth") {
        patch {
            val response = call.receive<ForgotPasswordAuthModel>()
            try {
                if (RESET_SERVICE.authenticateResponse(response.email, response.code)) {
                    val requestUUID = getUUIDOfResetRequest(response.email, response.code)

                    call.respond(RequestResultUUID(true, "Successfully", requestUUID))


                } else {
                    call.respond(RequestResultUUID(false, "Create a new Request", "N/A"))
                }
            } catch (e: Exception) {
                call.respond(RequestResultUUID(false, e.message.toString(), "N/A"))
            }

        }

    }


    route("/user/forgot-password/reset-password") {
        patch {
            val response = call.receive<ForgotPasswordResetModel>()
            try {
               if(!isUUIDFormat(response.uuid)){
                   throw Exception("Invalid UUID Format")
               }
                validatePasswordFormat(response.password)
                val email = provideEmailOfRequesterIfExist(response.uuid)
                userRepository.updateUserPasswordByEmail(email,response.password)
                call.respond(RequestResult(true, "Password Changed"))
                PasswordResetRequests.removeCandidateResetRequest(email)


            } catch (e: Exception) {

                call.respond(RequestResult(false, e.message.toString()))
            }
        }
    }


}