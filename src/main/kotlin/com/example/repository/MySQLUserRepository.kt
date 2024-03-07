package com.example.repository

import com.example.database.DBUserEntity
import com.example.database.DatabaseManager
import com.example.models.User
import com.example.models.UserLogin

class MySQLUserRepository() : UserRepository {
    private val database = DatabaseManager()
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