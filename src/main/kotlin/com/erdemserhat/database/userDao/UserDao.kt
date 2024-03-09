package com.erdemserhat.database.userDao

import com.erdemserhat.models.User
import com.erdemserhat.models.UserLogin

interface UserDao {
    fun getUserById(userId: Int): DBUserEntity?
    fun getUserByLoginInformation(userLogin: UserLogin): DBUserEntity?
    fun getAllUsers(): List<DBUserEntity>
    fun addUser(user: User): Int
    fun updateUserById(userId: Int, newUser: User): DBUserEntity?
    fun updateUserByLoginInformation(loginInformation: UserLogin, newUser: User): DBUserEntity?

    fun deleteUser(userId: Int): Boolean
    fun controlUserExistenceByEmail(email: String): Boolean
    fun deleteUserByLoginInformation(loginInformation: UserLogin): Boolean

    fun controlUserExistenceByAuth(loginInformation: UserLogin): Boolean

    fun updateUserPasswordByEmail(email:String, newPassword:String):DBUserEntity?
}