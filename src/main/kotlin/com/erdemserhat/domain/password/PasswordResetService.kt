package com.erdemserhat.domain.password

import com.erdemserhat.di.DatabaseModule.userRepository
import com.erdemserhat.domain.email.sendPasswordResetMail
import com.erdemserhat.util.RandomNumberGenerator.Companion.generateRandomAuthCode
import kotlinx.coroutines.*

@OptIn(DelicateCoroutinesApi::class)
class PasswordResetService(
    val email: String
) {
    private var code: String = generateRandomAuthCode(6)
    private var timer = 1200
    private var attempts = 3


    init {
        sendPasswordResetMail(email, code)
        GlobalScope.launch(Dispatchers.Default) {
            repeat(1200) {
                delay(1000)
                timer--

            }

        }
    }

    fun resetPassword(code: String, newPassword: String) {
        attempts--
        if (timer > 0) {
            if (attempts > 0) {
                if (this.code == code) {
                    userRepository.updateUserPasswordByEmail(email, newPassword)

                } else {
                    throw Exception("You have entered an incorrect code. You have $attempts attempts remaining. Please enter the correct code to proceed.")

                }

            } else {
                throw Exception("You have entered 3 times incorrect code, please request a new code for your safety.")
            }

        } else {
            throw Exception("For security reasons, you need to enter the code within a specified time frame. If you fail to do so, please request a new code for your safety.")
        }

    }


}