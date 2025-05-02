package com.erdemserhat.data.repository.user

import com.erdemserhat.data.database.sql.user.DBUserEntity
import com.erdemserhat.data.database.sql.user.UserDto
import com.erdemserhat.models.User
import com.erdemserhat.dto.requests.UserAuthenticationRequest

/**
 * Contract interface defining user repository operations.
 */
interface UserRepositoryContract {
    /**
     * Retrieves all users from the database.
     */
    suspend fun getAllUsers(): List<User>

    /**
     * Retrieves a user by their ID from the database.
     */
    suspend fun getUserById(userId: Int): User?

    /**
     * Retrieves a user by their login information from the database.
     */
    suspend fun getUserByLoginInformation(login: UserAuthenticationRequest): User?

    /**
     * Retrieves a user by their email from the database.
     */
    suspend fun getUserByEmailInformation(email: String): User?

    /**
     * Adds a new user to the database.
     */
    suspend fun addUser(user: User): Int

    /**
     * Updates a user by their ID in the database.
     */
    suspend fun updateUserById(userId: Int, newUser: User): DBUserEntity?

    /**
     * Updates a user by their email in the database.
     */
    suspend fun updateUserByEmail(email: String, newUser: User): DBUserEntity?

    /**
     * Deletes a user by their ID from the database.
     */
    suspend fun deleteUser(id: Int): Boolean

    /**
     * Checks if a user with the given email exists in the database.
     */
    suspend fun controlUserExistenceByEmail(email: String): Boolean

    /**
     * Deletes a user by their email from the database.
     */
    suspend fun deleteUserByEmailInformation(email: String): Boolean

    /**
     * Checks if a user with the given authentication information exists in the database.
     */
    suspend fun controlUserExistenceByAuth(login: UserAuthenticationRequest): Boolean

    /**
     * Updates a user's password by their email in the database.
     */
    suspend fun updateUserPasswordByEmail(email: String, newPassword: String): User?

    suspend fun enrolFcm(email:String, fcmId:String):Boolean

    suspend fun getUserByEmailMinimizedVersion(email: String): UserDto?

}
