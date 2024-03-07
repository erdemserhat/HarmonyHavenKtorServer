package com.example.di
import com.example.repository.MySQLUserRepository
import com.example.repository.UserRepository

object DatabaseModule{
    val userDao:UserRepository by lazy {
        MySQLUserRepository()
    }



}
