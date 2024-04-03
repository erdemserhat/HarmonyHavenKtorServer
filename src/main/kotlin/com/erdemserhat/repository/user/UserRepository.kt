package com.erdemserhat.repository.user

import com.erdemserhat.database.userDao.DBUserEntity
import com.erdemserhat.database.userDao.UserDao
import com.erdemserhat.database.userDao.UserDaoImpl
import com.erdemserhat.models.User
import com.erdemserhat.models.rest.client.UserAuthenticationRequest

class UserRepository() : UserRepositoryContract {
    private val database :UserDao = UserDaoImpl()
    override fun getAllUsers(): List<User> {
        return database.getAllUsers()
            .map {
                User(
                    it.id,
                    it.name,
                    it.surname,
                    it.email,
                    it.password,
                    it.gender,
                    it.profilePhotoPath,
                    it.fcmId,
                    it.uuid,
                    it.role
                )
            }
    }

    override fun getUserById(userId: Int): User? {
        return database.getUserById(userId)?.toUser()
    }

    override fun getUserByLoginInformation(login: UserAuthenticationRequest): User? {
        return  database.getUserByLoginInformation(login)?.toUser()
    }

    override fun getUserByEmailInformation(email: String): User? {
        return database.getUserByEmail(email)?.toUser()
    }

    override fun addUser(user: User): Int {
        return database.addUser(user)
    }

    override fun updateUserById(userId: Int, newUser: User): DBUserEntity? {
        return database.updateUserById(userId,newUser)
    }

    override fun updateUserByLoginInformation(login: UserAuthenticationRequest, newUser: User): DBUserEntity? {
        return database.updateUserByLoginInformation(login, newUser)
    }


    override fun deleteUser(id: Int): Boolean {
        return database.deleteUser(id)
    }

    override fun controlUserExistenceByEmail(email: String): Boolean {
       return  database.controlUserExistenceByEmail(email)
    }

    override fun deleteUserByEmailInformation(email:String): Boolean {
        return database.deleteUserByEmailInformation(email)
    }

    override fun controlUserExistenceByAuth(login: UserAuthenticationRequest): Boolean {
        return database.controlUserExistenceByAuth(login)
    }

    override fun updateUserPasswordByEmail(email: String, newPassword: String): User? {
        return database.updateUserPasswordByEmail(email,newPassword)?.toUser()
    }

    private fun DBUserEntity.toUser(): User {
        return User(id, name, surname, email, password, gender, profilePhotoPath,fcmId,uuid,role)
    }


}