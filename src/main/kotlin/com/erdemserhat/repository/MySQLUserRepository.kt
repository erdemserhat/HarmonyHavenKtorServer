package com.erdemserhat.repository

import com.erdemserhat.database.userDao.DBUserEntity
import com.erdemserhat.database.userDao.UserDao
import com.erdemserhat.database.userDao.UserDaoImpl
import com.erdemserhat.models.User
import com.erdemserhat.models.UserLogin

class MySQLUserRepository() : UserRepository {
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
                    it.profilePhotoPath
                )
            }
    }

    override fun getUserById(userId: Int): User? {
        return database.getUserById(userId)?.toUser()
    }

    override fun getUserByLoginInformation(login: UserLogin): User? {
        return  database.getUserByLoginInformation(login)?.toUser()
    }

    override fun addUser(user: User): Int {
        return database.addUser(user)
    }

    override fun updateUserById(userId: Int, newUser: User): DBUserEntity? {
        return database.updateUserById(userId,newUser)
    }

    override fun updateUserByLoginInformation(login: UserLogin, newUser: User): DBUserEntity? {
        return database.updateUserByLoginInformation(login, newUser)
    }


    override fun deleteUser(id: Int): Boolean {
        return database.deleteUser(id)
    }

    override fun controlUserExistenceByEmail(email: String): Boolean {
       return  database.controlUserExistenceByEmail(email)
    }

    override fun deleteUserByLoginInformation(login: UserLogin): Boolean {
        return database.deleteUserByLoginInformation(login)
    }

    override fun controlUserExistenceByAuth(login: UserLogin): Boolean {
        return database.controlUserExistenceByAuth(login)
    }

    private fun DBUserEntity.toUser(): User {
        return User(id, name, surname, email, password, gender, profilePhotoPath)
    }


}