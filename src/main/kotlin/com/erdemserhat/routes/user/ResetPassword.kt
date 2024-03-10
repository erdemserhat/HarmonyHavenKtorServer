package com.erdemserhat.routes.user

import com.erdemserhat.di.DatabaseModule
import com.erdemserhat.domain.password.PasswordResetService
import com.erdemserhat.domain.validation.validateEmailFormat
import com.erdemserhat.domain.validation.validatePasswordFormat
import com.erdemserhat.models.Message
import com.erdemserhat.models.PasswordResetModel
import com.erdemserhat.models.ResetPasswordRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * This function handles the user's reset password request.
 * There are some conditions which user provide ;
 * 1 -> User has to enter valid code within maximum three attempts
 * 2 -> User has to enter valid code within 20 min
 * 3 ->User has to define valid password for security
 */

fun Route.resetPassword() {
    var passwordResetService: PasswordResetService? = null
    route("/user/reset-password/auth") {
        post {
            try {

                val resetPasswordRequest = call.receive<ResetPasswordRequest>()
                validateEmailFormat(resetPasswordRequest.email)

                if (DatabaseModule.userRepository.controlUserExistenceByEmail(resetPasswordRequest.email)) {
                    passwordResetService = PasswordResetService(resetPasswordRequest.email)
                    call.respond(HttpStatusCode.OK, Message("OK"))

                } else {
                    call.respond(HttpStatusCode.NotFound, Message("User not found"))
                }

            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, Message(e.message.toString()))
            }

        }


    }

    route("/user/resetpassword/confirm") {
        patch {
            try {
                if (passwordResetService != null) {
                    val passwordResetData = call.receive<PasswordResetModel>()
                    validatePasswordFormat(passwordResetData.newPassword)
                    passwordResetService!!.resetPassword(passwordResetData.code, passwordResetData.newPassword)
                    passwordResetService=null
                    call.respond(HttpStatusCode.OK,Message("Password changed."))


                } else {
                    call.respond(HttpStatusCode.Unauthorized, Message("You must start password reset service firstly"))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, Message(e.message.toString()))
            }

        }

    }


}