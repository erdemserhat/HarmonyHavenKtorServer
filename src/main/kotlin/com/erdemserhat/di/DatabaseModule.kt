package com.erdemserhat.di
import com.erdemserhat.repository.MySQLUserRepository
import com.erdemserhat.repository.UserRepository

object DatabaseModule{
    val userRepository:UserRepository by lazy {
        MySQLUserRepository()
    }



}
