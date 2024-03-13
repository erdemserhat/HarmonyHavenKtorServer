package com.erdemserhat.di
import com.erdemserhat.domain.password.PasswordResetService
import com.erdemserhat.repository.MySQLUserRepository
import com.erdemserhat.repository.UserRepository

object DatabaseModule{
    val userRepository:UserRepository by lazy {
        MySQLUserRepository()
    }

    val passwordResetService = PasswordResetService()



}
