package com.erdemserhat.romote.database.user

import com.erdemserhat.models.User
import com.erdemserhat.dto.requests.UserAuthenticationRequest

/**
 * Interface for user data access operations.
 */
interface UserDao {

    // Retrieve a user by ID
    fun getUserById(userId: Int): DBUserEntity?

    // Retrieve a user by login information
    fun getUserByLoginInformation(userLogin: UserAuthenticationRequest): DBUserEntity?

    // Retrieve all users
    fun getAllUsers(): List<DBUserEntity>

    // Add a new user
    fun addUser(user: User): Int

    // Update a user by ID
    fun updateUserById(userId: Int, newUser: User): DBUserEntity?

    // Update a user by email
    fun updateUserByEmail(email: String, newUser: User): DBUserEntity?

    // Delete a user by ID
    fun deleteUser(userId: Int): Boolean

    // Check if a user exists by email
    fun controlUserExistenceByEmail(email: String): Boolean

    // Delete a user by email
    fun deleteUserByEmailInformation(email: String): Boolean

    // Check if a user exists by authentication
    fun controlUserExistenceByAuth(loginInformation: UserAuthenticationRequest): Boolean

    // Update user password by email
    fun updateUserPasswordByEmail(email: String, newPassword: String): DBUserEntity?

    // Get a user by email
    fun getUserByEmail(email: String): DBUserEntity?

    fun enrolFcm(email:String, fcmId:String):Boolean
}
