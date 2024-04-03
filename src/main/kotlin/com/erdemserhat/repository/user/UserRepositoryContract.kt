package com.erdemserhat.repository.user

import com.erdemserhat.database.userDao.DBUserEntity
import com.erdemserhat.models.User
import com.erdemserhat.models.rest.client.UserAuthenticationRequest

interface UserRepositoryContract {
    fun getAllUsers():List<User>

    fun getUserById(userId:Int):User?

    fun getUserByLoginInformation(login: UserAuthenticationRequest):User?

    fun getUserByEmailInformation(email:String):User?

    fun addUser(user:User):Int

    fun updateUserById(userId: Int, newUser:User): DBUserEntity?

    fun updateUserByLoginInformation(login: UserAuthenticationRequest, newUser: User): DBUserEntity?

    fun deleteUser(id:Int):Boolean

    fun controlUserExistenceByEmail(email:String):Boolean

    fun deleteUserByEmailInformation(email:String):Boolean

    fun controlUserExistenceByAuth(login: UserAuthenticationRequest):Boolean

    fun updateUserPasswordByEmail(email:String,newPassword:String):User?



}