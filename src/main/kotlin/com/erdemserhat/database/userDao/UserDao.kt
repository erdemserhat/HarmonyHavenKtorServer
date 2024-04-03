package com.erdemserhat.database.userDao

import com.erdemserhat.models.User
import com.erdemserhat.models.rest.client.UserAuthenticationRequest


interface UserDao {
    fun getUserById(userId: Int): DBUserEntity?
    fun getUserByLoginInformation(userLogin: UserAuthenticationRequest): DBUserEntity?
    fun getAllUsers(): List<DBUserEntity>
    fun addUser(user: User): Int
    fun updateUserById(userId: Int, newUser: User): DBUserEntity?
    fun updateUserByLoginInformation(loginInformation: UserAuthenticationRequest, newUser: User): DBUserEntity?

    fun deleteUser(userId: Int): Boolean
    fun controlUserExistenceByEmail(email: String): Boolean
    fun deleteUserByEmailInformation(email:String): Boolean

    fun controlUserExistenceByAuth(loginInformation: UserAuthenticationRequest): Boolean

    fun updateUserPasswordByEmail(email:String, newPassword:String):DBUserEntity?

    fun getUserByEmail(email:String):DBUserEntity?
}