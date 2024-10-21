package com.erdemserhat.data.repository.user

import com.erdemserhat.data.database.user.DBUserEntity
import com.erdemserhat.data.database.user.UserDao
import com.erdemserhat.data.database.user.UserDaoImpl
import com.erdemserhat.models.User
import com.erdemserhat.dto.requests.UserAuthenticationRequest

/**
 * Repository class for user-related operations.
 */
class UserRepository : UserRepositoryContract {
    private val database: UserDao = UserDaoImpl()
    override suspend fun getAllUsers(): List<User> {
        return database.getAllUsers().map { it.toUser() }
    }

    override suspend fun getUserById(userId: Int): User? {
        return database.getUserById(userId)?.toUser()
    }

    override suspend fun getUserByLoginInformation(login: UserAuthenticationRequest): User? {
        return database.getUserByLoginInformation(login)?.toUser()
    }

    override suspend fun getUserByEmailInformation(email: String): User? {
        return database.getUserByEmail(email)?.toUser()
    }

    override suspend fun addUser(user: User): Int {
        return database.addUser(user)
    }

    override suspend fun updateUserById(userId: Int, newUser: User): DBUserEntity? {
        return database.updateUserById(userId, newUser)
    }

    override suspend fun updateUserByEmail(email: String, newUser: User): DBUserEntity? {
        return database.updateUserByEmail(email, newUser)
    }

    override suspend fun deleteUser(id: Int): Boolean {
        return database.deleteUser(id)
    }

    override suspend fun controlUserExistenceByEmail(email: String): Boolean {
        return database.controlUserExistenceByEmail(email)
    }

    override suspend fun deleteUserByEmailInformation(email: String): Boolean {
        return database.deleteUserByEmailInformation(email)
    }

    override suspend fun controlUserExistenceByAuth(login: UserAuthenticationRequest): Boolean {
        return database.controlUserExistenceByAuth(login)
    }

    override suspend fun updateUserPasswordByEmail(email: String, newPassword: String): User? {
        return database.updateUserPasswordByEmail(email, newPassword)?.toUser()
    }

    override suspend fun enrolFcm(email: String, fcmId: String): Boolean {
        return database.enrolFcm(email,fcmId)
    }

    private fun DBUserEntity.toUser(): User {
        return User(id, name, surname, email, password, gender, profilePhotoPath, fcmId, uuid, role)
    }
}
