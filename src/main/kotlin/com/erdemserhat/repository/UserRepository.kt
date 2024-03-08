package com.erdemserhat.repository

import com.erdemserhat.database.userDao.DBUserEntity
import com.erdemserhat.models.User
import com.erdemserhat.models.UserLogin

interface UserRepository {
    fun getAllUsers():List<User>

    fun getUserById(userId:Int):User?

    fun getUserByLoginInformation(login: UserLogin):User?

    fun addUser(user:User):Int

    fun updateUserById(userId: Int, newUser:User): DBUserEntity?

    fun updateUserByLoginInformation(login: UserLogin, newUser: User): DBUserEntity?

    fun deleteUser(id:Int):Boolean

    fun controlUserExistenceByEmail(email:String):Boolean

    fun deleteUserByLoginInformation(login: UserLogin):Boolean

    fun controlUserExistenceByAuth(login: UserLogin):Boolean



}