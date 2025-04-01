package com.erdemserhat.data.database.user

import com.erdemserhat.models.User
import com.erdemserhat.dto.requests.UserAuthenticationRequest

/**
 * Interface for user data access operations.
 */
interface UserDao {

    // Retrieve a user by ID
    suspend fun getUserById(userId: Int): DBUserEntity?

    // Retrieve a user by login information
    suspend fun getUserByLoginInformation(userLogin: UserAuthenticationRequest): DBUserEntity?

    // Retrieve all users
    suspend fun getAllUsers(): List<DBUserEntity>

    // Add a new user
    suspend fun addUser(user: User): Int

    // Update a user by ID
    suspend fun updateUserById(userId: Int, newUser: User): DBUserEntity?

    // Update a user by email
    suspend fun updateUserByEmail(email: String, newUser: User): DBUserEntity?

    // Delete a user by ID
    suspend fun deleteUser(userId: Int): Boolean

    // Check if a user exists by email
    suspend fun controlUserExistenceByEmail(email: String): Boolean

    // Delete a user by email
    suspend fun deleteUserByEmailInformation(email: String): Boolean

    // Check if a user exists by authentication
    suspend fun controlUserExistenceByAuth(loginInformation: UserAuthenticationRequest): Boolean

    // Update user password by email
    suspend fun updateUserPasswordByEmail(email: String, newPassword: String): DBUserEntity?

    // Get a user by email
    suspend fun getUserByEmail(email: String): DBUserEntity?

    suspend fun getUserByEmailMinimizedVersion(email: String): UserDto?

    suspend fun enrolFcm(email:String, fcmId:String):Boolean
}
