package com.erdemserhat.routes.user

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.erdemserhat.data.sendWelcomeNotification
import com.erdemserhat.dto.requests.GoogleAuthenticationRequest
import com.erdemserhat.dto.responses.GoogleAuthenticationResponse
import com.erdemserhat.models.UserInformationSchema
import com.erdemserhat.models.toUser
import com.erdemserhat.service.configurations.validateGoogleIdToken
import com.erdemserhat.service.di.AuthenticationModule
import com.erdemserhat.service.di.DatabaseModule
import com.erdemserhat.service.security.hashPassword
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import io.ktor.http.*

import io.ktor.server.response.*
import java.util.*


fun Route.googleLogin() {
    post("/user/authenticate-google") {
        // Assuming you're receiving the ID token from the request body
        val idTokenString = call.receive<GoogleAuthenticationRequest>()

        //validate google id token
        val validatedToken = call.validateGoogleIdToken(idTokenString.googleIdToken)

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
            val potentialUser = DatabaseModule.userRepository.getUserByEmailInformation(email)

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
                sendWelcomeNotification(email)
                val userRegistered = DatabaseModule.userRepository.getUserByEmailInformation(email)

                //TODO : OPTIMIZE HERE JWT GENERATION SHOULD NOT BE HERE DIRECTLY

                val token = JWT.create()
                    .withAudience(AuthenticationModule.tokenConfigSecurity.audience)
                    .withIssuer(AuthenticationModule.tokenConfigSecurity.issuer)
                    .withClaim("email", userRegistered!!.email)
                    .withClaim("role", userRegistered.role)
                    .withExpiresAt(Date(System.currentTimeMillis() + 600000000000)) // Token expiration time
                    .sign(Algorithm.HMAC256(AuthenticationModule.tokenConfigSecurity.secret))

                call.respond(
                    status = HttpStatusCode.OK,
                    message = GoogleAuthenticationResponse(
                        jwt = token
                    )
                )


              // if there is already a record with that email just give the jwt
            }else{
                val existingUser = DatabaseModule.userRepository.getUserByEmailInformation(email)

                //TODO : OPTIMIZE HERE JWT GENERATION SHOULD NOT BE HERE DIRECTLY
                val token = JWT.create()
                    .withAudience(AuthenticationModule.tokenConfigSecurity.audience)
                    .withIssuer(AuthenticationModule.tokenConfigSecurity.issuer)
                    .withClaim("email", existingUser!!.email)
                    .withClaim("role", existingUser.role)
                    .withExpiresAt(Date(System.currentTimeMillis() + 600000000000)) // Token expiration time
                    .sign(Algorithm.HMAC256(AuthenticationModule.tokenConfigSecurity.secret))

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
    }
}
