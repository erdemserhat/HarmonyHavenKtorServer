package com.example.dao

import com.example.models.User

interface UserDaoFacade {
    suspend fun allUsers(): List<User>
    suspend fun user(id: Int): User?
    suspend fun addNewUser(
        name: String,
        surname: String,
        email: String,
        password: String,
        gender: String,
        profilePhoto:String

        ):User?

    suspend fun editUser(
        id: Int,
        name:String,
        surname:String,
        email:String,
        password: String,

    ):Boolean

    suspend fun deleteUser(id:Int):Boolean


}
