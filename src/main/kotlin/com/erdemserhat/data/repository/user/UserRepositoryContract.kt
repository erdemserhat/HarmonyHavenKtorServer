package com.erdemserhat.data.repository.user

import com.erdemserhat.data.database.user.DBUserEntity
import com.erdemserhat.models.User
import com.erdemserhat.dto.requests.UserAuthenticationRequest

/**
 * Contract interface defining user repository operations.
 */
interface UserRepositoryContract {
    /**
     * Retrieves all users from the database.
     */
    fun getAllUsers(): List<User>

    /**
     * Retrieves a user by their ID from the database.
     */
    fun getUserById(userId: Int): User?

    /**
     * Retrieves a user by their login information from the database.
     */
    fun getUserByLoginInformation(login: UserAuthenticationRequest): User?

    /**
     * Retrieves a user by their email from the database.
     */
    fun getUserByEmailInformation(email: String): User?

    /**
     * Adds a new user to the database.
     */
    fun addUser(user: User): Int

    /**
     * Updates a user by their ID in the database.
     */
    fun updateUserById(userId: Int, newUser: User): DBUserEntity?

    /**
     * Updates a user by their email in the database.
     */
    fun updateUserByEmail(email: String, newUser: User): DBUserEntity?

    /**
     * Deletes a user by their ID from the database.
     */
    fun deleteUser(id: Int): Boolean

    /**
     * Checks if a user with the given email exists in the database.
     */
    fun controlUserExistenceByEmail(email: String): Boolean

    /**
     * Deletes a user by their email from the database.
     */
    fun deleteUserByEmailInformation(email: String): Boolean

    /**
     * Checks if a user with the given authentication information exists in the database.
     */
    fun controlUserExistenceByAuth(login: UserAuthenticationRequest): Boolean

    /**
     * Updates a user's password by their email in the database.
     */
    fun updateUserPasswordByEmail(email: String, newPassword: String): User?

    fun enrolFcm(email:String, fcmId:String):Boolean

}
