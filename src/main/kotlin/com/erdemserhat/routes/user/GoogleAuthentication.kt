package com.erdemserhat.routes.user

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.erdemserhat.data.database.sql.user.UserDto
import com.erdemserhat.data.mail.sendWelcomeMail
import com.erdemserhat.dto.requests.FcmNotification
import com.erdemserhat.dto.requests.GoogleAuthenticationRequest
import com.erdemserhat.dto.requests.SendNotificationSpecific
import com.erdemserhat.dto.requests.toFcmMessage
import com.erdemserhat.dto.responses.GoogleAuthenticationResponse
import com.erdemserhat.models.UserInformationSchema
import com.erdemserhat.models.toUser
import com.erdemserhat.service.configurations.validateGoogleIdToken
import com.erdemserhat.service.di.AuthenticationModule
import com.erdemserhat.service.di.DatabaseModule
import com.erdemserhat.service.security.hashPassword
import io.ktor.server.request.*
import io.ktor.server.routing.*
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.firebase.messaging.FirebaseMessaging
import io.ktor.http.*
import io.ktor.server.plugins.*

import io.ktor.server.response.*
import kotlinx.coroutines.*
import java.util.*


@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
fun Route.googleLogin() {
    post("/user/authenticate-google") {
        val startedTime = Date().time
        // Assuming you're receiving the ID token from the request body
        val idTokenString = call.receive<GoogleAuthenticationRequest>()

        //validate google id token
        val x = Date().time
        val validatedToken = call.validateGoogleIdToken(idTokenString.googleIdToken)
        println("token validated: ${Date().time - x}ms")

        var potentialUser: UserDto? = null
        //if token is valid..
        if (validatedToken != null) {
            val payload: Payload = validatedToken.payload


            // Get profile information from payload  below you can use all the information
            //when I implemented this, I just needed three of them (email, pictureUrl,givenName)
            val email: String = payload.email
            //val emailVerified: Boolean = payload.emailVerified
            //val name: String? = payload["name"] as? String
            val pictureUrl: String? = payload["picture"] as? String
            //val locale: String? = payload["locale"] as? String
            //val familyName: String? = payload["family_name"] as? String
            val givenName: String? = payload["given_name"] as? String

            //try to reach user record from database
            //3000ms
            potentialUser = DatabaseModule.userRepository.getUserByEmailMinimizedVersion(email)
            println(potentialUser)
            //3000ms

            //if there is no record, register the user

            if(potentialUser==null){
                //user unregistered register and return a jwt
                val user =UserInformationSchema(
                    name = givenName?:"",
                    email = email,
                    profilePhotoPath = pictureUrl?:"-",
                    gender = "-",
                    password = hashPassword(UUID.randomUUID().toString()),
                ).toUser()

                DatabaseModule.userRepository.addUser(user)

                val userRegistered = DatabaseModule.userRepository.getUserByEmailMinimizedVersion(email)

                //TODO : OPTIMIZE HERE JWT GENERATION SHOULD NOT BE HERE DIRECTLY

                val token = JWT.create()
                    .withAudience(AuthenticationModule.tokenConfigSecurity.audience)
                    .withIssuer(AuthenticationModule.tokenConfigSecurity.issuer)
                    .withClaim("email", userRegistered!!.email)
                    .withClaim("role", userRegistered.role)
                    .withClaim("id",userRegistered.id)
                    .withExpiresAt( Date(System.currentTimeMillis() + 315360000000L))
                    .sign(Algorithm.HMAC256(AuthenticationModule.tokenConfigSecurity.secret))

                call.application.launch(Dispatchers.IO) {
                    launch { sendWelcomeMail(to = userRegistered.email, name = userRegistered.name)  }
                    launch {
                        val ipAddress = call.request.origin.remoteHost
                        val informationMessage =
                            SendNotificationSpecific(
                                email = "serhaterdem961@gmail.com",
                                notification = FcmNotification(
                                    title = "Yeni Bir Üye Katıldı: ${userRegistered.name} 😊",
                                    body = "Merhaba! ${userRegistered.name}, ${userRegistered.email} (${ipAddress}) adresiyle Harmony Haven'a katıldı. Bir göz atmak isteyebilirsiniz! 👀",
                                    screen = "1"
                                )
                            )

                        FirebaseMessaging.getInstance().send(informationMessage.toFcmMessage())

                    }
                }

                call.response.cookies.append(
                    Cookie(
                        maxAge = 36000000,
                        name = "auth_token",
                        value = token,
                        path = "/",
                        httpOnly = true,
                        secure = true, //
                        extensions = mapOf("SameSite" to "None")
                    )
                )



                call.respond(
                    status = HttpStatusCode.OK,
                    message = GoogleAuthenticationResponse(
                        jwt = token
                    )
                )


              // if there is already a record with that email just give the jwt
            }else{
                val existingUser = potentialUser

                //TODO : OPTIMIZE HERE JWT GENERATION SHOULD NOT BE HERE DIRECTLY
                val token = JWT.create()
                    .withAudience(AuthenticationModule.tokenConfigSecurity.audience)
                    .withIssuer(AuthenticationModule.tokenConfigSecurity.issuer)
                    .withClaim("email", existingUser!!.email)
                    .withClaim("role", existingUser.role)
                    .withClaim("id",existingUser.id)
                    .withExpiresAt( Date(System.currentTimeMillis() + 315360000000L))
                    .sign(Algorithm.HMAC256(AuthenticationModule.tokenConfigSecurity.secret))



                call.response.cookies.append(
                    Cookie(
                        maxAge = 36000000,
                        name = "auth_token",
                        value = token,
                        path = "/",
                        httpOnly = true,
                        secure = true, //
                        extensions = mapOf("SameSite" to "None")
                    )
                )

                call.respond(
                    status = HttpStatusCode.OK,
                    message = GoogleAuthenticationResponse(
                        jwt = token
                    )
                )



            }

            // Respond with user information or generate JWT
            call.respond(HttpStatusCode.OK, "User authenticated: $givenName, $email")
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Invalid ID token.")
        }
        println("processed:: ${Date().time -startedTime}")
    }
}
