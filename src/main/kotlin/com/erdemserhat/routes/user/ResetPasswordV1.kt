package com.erdemserhat.routes.user

import com.erdemserhat.service.di.DatabaseModule.userRepository
import com.erdemserhat.service.pwrservice.PasswordResetRequestsPool
import com.erdemserhat.service.validation.*
import com.erdemserhat.dto.requests.ForgotPasswordAuthModel
import com.erdemserhat.dto.requests.ForgotPasswordMailerModel
import com.erdemserhat.dto.requests.ForgotPasswordResetModel
import com.erdemserhat.dto.requests.UserAuthenticationRequest
import com.erdemserhat.dto.responses.PasswordResetResponse
import com.erdemserhat.dto.responses.RequestResultUUID
import com.erdemserhat.service.security.hashPassword
import com.erdemserhat.util.isUUIDFormat
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import com.erdemserhat.service.di.DatabaseModule.passwordResetService as RESET_SERVICE

/**
 * This function handles the user's reset password request.
 * There are some conditions which user provide ;
 * 1 -> User has to enter valid code within maximum three attempts
 * 2 -> User has to enter valid code within 20 min
 * 3 ->User has to define valid password for security
 */

@Serializable
data class PasswordResetMailerResponse(
    val result: Boolean,
    val message: String
)

@Serializable
data class PasswordResetAuthenticateResponse(
    val result: Boolean,
    val message: String,
    val uuid: String = "-"
)

data class PasswordResetFinalResponse(
    val result:Boolean,
    val message:String
)


fun Route.resetPasswordV1() {
    route("/api/v1/user/forgot-password/mailer") {
        post {
            try {
                val resetPasswordRequest = call.receive<ForgotPasswordMailerModel>()

                if (!isValidEmailFormat(resetPasswordRequest.email)) {
                    call.respond(
                        status = HttpStatusCode.UnprocessableEntity,
                        message = PasswordResetMailerResponse(false, "Invalid email format.")
                    )
                    return@post

                }
                if (!userRepository.controlUserExistenceByEmail(resetPasswordRequest.email)) {
                    call.respond(
                        status = HttpStatusCode.UnprocessableEntity,
                        message = PasswordResetMailerResponse(false, "There is no user with this email")
                    )
                    return@post

                }

                val requestResult = RESET_SERVICE.createRequest(resetPasswordRequest.email)

                if (!requestResult.result) {
                    call.respond(
                        status = HttpStatusCode.UnprocessableEntity,
                        message = PasswordResetMailerResponse(false, requestResult.message)
                    )
                    return@post
                }


                call.respond(
                    status = HttpStatusCode.OK,
                    message = PasswordResetMailerResponse(true, requestResult.message)
                )


            } catch (e: Exception) {
                call.respond(
                    status = HttpStatusCode.InternalServerError,
                    message = PasswordResetMailerResponse(false, "Internal Server Error")
                )
            }

        }


    }

    route("/api/v1/user/forgot-password/auth") {
        patch {
            val request = call.receive<ForgotPasswordAuthModel>()
            try {

                val authRequest = RESET_SERVICE.authenticateRequest(request.email, request.code)

                if (!authRequest.result) {
                    call.respond(
                        status = HttpStatusCode.UnprocessableEntity,
                        message = PasswordResetAuthenticateResponse(false, authRequest.message)
                    )
                    return@patch

                }

                val requestUUIDResult = PasswordResetRequestsPool.getUUIDOfResetRequest(request.email, request.code)

                if (!requestUUIDResult.result) {
                    call.respond(
                        status = HttpStatusCode.TooManyRequests,
                        message = PasswordResetAuthenticateResponse(false, requestUUIDResult.message)
                    )
                    return@patch

                }

                call.respond(
                    status = HttpStatusCode.Accepted,
                    message = PasswordResetAuthenticateResponse(true, "Successfully", requestUUIDResult.message)

                )


            } catch (e: Exception) {
                call.respond(
                    status = HttpStatusCode.InternalServerError,
                    message = PasswordResetAuthenticateResponse(false,e.message.toString())
                )
                println(e.printStackTrace())
            }

        }

    }


    route("/api/v1/user/forgot-password/reset-password") {
        patch {
            val response = call.receive<ForgotPasswordResetModel>()
            try {

                if (!isUUIDFormat(response.uuid)) {
                    call.respond(
                        status = HttpStatusCode.UnprocessableEntity,
                        message = PasswordResetFinalResponse(false,"Invalid password request uuid format")
                    )
                    return@patch
                }

                val validator = UserAuthenticationInputValidatorService(
                    UserAuthenticationRequest(
                        email = "",
                        password = response.password
                    )
                )

                val passwordValidationResult = validator.validatePassword()

                if (!passwordValidationResult.isValid) {
                    call.respond(
                        status = HttpStatusCode.UnprocessableEntity,
                        message = PasswordResetFinalResponse(false,passwordValidationResult.errorMessage)
                    )
                    return@patch
                }

                val provideEmailOfRequesterIfExistResult =
                    PasswordResetRequestsPool.provideEmailOfRequesterIfExist(response.uuid)

                if (!provideEmailOfRequesterIfExistResult.result) {
                    call.respond(
                        status = HttpStatusCode.UnprocessableEntity,
                        message = PasswordResetFinalResponse(false,provideEmailOfRequesterIfExistResult.message)
                    )
                    return@patch

                }

                val userEmail = provideEmailOfRequesterIfExistResult.message
                userRepository.updateUserPasswordByEmail(userEmail, hashPassword(response.password))

                PasswordResetRequestsPool.usePermission(userEmail)
                call.respond(
                    status = HttpStatusCode.OK,
                    message = PasswordResetFinalResponse(true,"Password changed successfully.")
                )


            } catch (e: Exception) {

                call.respond(
                    status = HttpStatusCode.InternalServerError,
                    message = PasswordResetFinalResponse(false,"Internal Server Error")
                )
            }
        }
    }
}


