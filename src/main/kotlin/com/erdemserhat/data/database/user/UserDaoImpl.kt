package com.erdemserhat.data.database.user

import com.erdemserhat.data.database.DatabaseConfig.ktormDatabase
import com.erdemserhat.models.User
import com.erdemserhat.dto.requests.UserAuthenticationRequest
import org.ktorm.dsl.*
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList
import java.util.*

/**
 * Implementation of the UserDao interface, responsible for performing database operations related to users using KTorm.
 */
class UserDaoImpl : UserDao {

    /**
     * Retrieves a user by their ID.
     * @param userId The ID of the user to retrieve.
     * @return The user entity if found, otherwise null.
     */
    override suspend fun getUserById(userId: Int): DBUserEntity? {
        return ktormDatabase.sequenceOf(DBUserTable)
            .firstOrNull { DBUserTable.id eq userId }
    }

    /**
     * Retrieves a user by their login information.
     * @param userLogin The user's login information.
     * @return The user entity if found, otherwise null.
     */
    override suspend fun getUserByLoginInformation(userLogin: UserAuthenticationRequest): DBUserEntity? {
        return ktormDatabase.sequenceOf(DBUserTable)
            .firstOrNull { (DBUserTable.email eq userLogin.email) and (DBUserTable.password eq userLogin.password) }
    }

    /**
     * Retrieves all users from the database.
     * @return A list of user entities.
     */
    override suspend fun getAllUsers(): List<DBUserEntity> {
        return ktormDatabase.sequenceOf(DBUserTable).toList()
    }

    /**
     * Adds a new user to the database.
     * @param user The user to add.
     * @return The ID of the newly added user.
     */
    override suspend fun addUser(user: User): Int {
        return ktormDatabase.insert(DBUserTable) {
            set(DBUserTable.name, user.name)
            set(DBUserTable.surname, user.surname)
            set(DBUserTable.email, user.email)
            set(DBUserTable.password, user.password)
            set(DBUserTable.gender, user.gender)
            set(DBUserTable.profilePhotoPath, user.profilePhotoPath)
            set(DBUserTable.fcmId, user.fcmId)
            set(DBUserTable.uuid, user.uuid)
            set(DBUserTable.role, user.role)
        }
    }

    /**
     * Updates a user by their ID.
     * @param userId The ID of the user to update.
     * @param newUser The new user information.
     * @return The updated user entity.
     */
    override suspend fun updateUserById(userId: Int, newUser: User): DBUserEntity? {
        ktormDatabase.update(DBUserTable) {
            set(DBUserTable.name, newUser.name)
            set(DBUserTable.surname, newUser.surname)
            set(DBUserTable.email, newUser.email)
            set(DBUserTable.password, newUser.password)
            set(DBUserTable.gender, newUser.gender)
            set(DBUserTable.profilePhotoPath, newUser.profilePhotoPath)
            set(DBUserTable.fcmId, newUser.fcmId)
            set(DBUserTable.uuid, newUser.uuid)
            set(DBUserTable.role, newUser.role)
            where {
                DBUserTable.id eq userId
            }
        }
        return ktormDatabase.sequenceOf(DBUserTable)
            .firstOrNull { DBUserTable.id eq userId }
    }

    /**
     * Updates a user by their email.
     * @param email The email of the user to update.
     * @param newUser The new user information.
     * @return The updated user entity.
     */
    override suspend fun updateUserByEmail(email: String, newUser: User): DBUserEntity? {
        ktormDatabase.update(DBUserTable) {
            set(DBUserTable.name, newUser.name)
            set(DBUserTable.surname, newUser.surname)
            set(DBUserTable.email, newUser.email)
            set(DBUserTable.password, newUser.password)
            set(DBUserTable.gender, newUser.gender)
            set(DBUserTable.profilePhotoPath, newUser.profilePhotoPath)
            set(DBUserTable.fcmId, newUser.fcmId)
            set(DBUserTable.uuid, newUser.uuid)
            set(DBUserTable.role, newUser.role)
            where {
                (DBUserTable.email eq email)
            }
        }
        return ktormDatabase.sequenceOf(DBUserTable)
            .firstOrNull { (DBUserTable.email eq email) }
    }

    /**
     * Deletes a user by their ID.
     * @param userId The ID of the user to delete.
     * @return True if the user was deleted, false otherwise.
     */
    override suspend fun deleteUser(userId: Int): Boolean {
        val affectedRows = ktormDatabase.delete(DBUserTable) {
            DBUserTable.id eq userId
        }
        return affectedRows > 0
    }

    /**
     * Checks if a user with the given email exists.
     * @param email The email to check.
     * @return True if the user exists, false otherwise.
     */
    override suspend fun controlUserExistenceByEmail(email: String): Boolean {
        val user = ktormDatabase.sequenceOf(DBUserTable)
            .firstOrNull { DBUserTable.email eq email }
        return user != null
    }

    /**
     * Deletes a user by their email.
     * @param email The email of the user to delete.
     * @return True if the user was deleted, false otherwise.
     */
    override suspend fun deleteUserByEmailInformation(email: String): Boolean {
        val affectedRows = ktormDatabase.delete(DBUserTable) {
            (DBUserTable.email eq email)
        }
        return affectedRows > 0
    }

    /**
     * Checks if a user with the given login information exists.
     * @param loginInformation The login information to check.
     * @return True if the user exists, false otherwise.
     */
    override suspend fun controlUserExistenceByAuth(loginInformation: UserAuthenticationRequest): Boolean {
        val user = ktormDatabase.sequenceOf(DBUserTable)
            .firstOrNull { (DBUserTable.email eq loginInformation.email) and (DBUserTable.password eq loginInformation.password) }
        return user != null
    }

    /**
     * Updates a user's password by their email.
     * @param email The email of the user.
     * @param newPassword The new password.
     * @return The updated user entity.
     */
    override suspend fun updateUserPasswordByEmail(email: String, newPassword: String): DBUserEntity? {
        ktormDatabase.update(DBUserTable) {
            set(DBUserTable.password, newPassword)
            where {
                DBUserTable.email eq email
            }
        }
        return ktormDatabase.sequenceOf(DBUserTable)
            .firstOrNull { DBUserTable.email eq email }
    }

    /**
     * Retrieves a user by their email.
     * @param email The email of the user to retrieve.
     * @return The user entity if found, otherwise null.
     */
    override suspend fun getUserByEmail(email: String): DBUserEntity? {
        return ktormDatabase.sequenceOf(DBUserTable)
            .firstOrNull {
                DBUserTable.email eq email
            }
    }

    override suspend fun getUserByEmailMinimizedVersion(email: String): UserDto? {
        val x = Date().time
        val query = "SELECT * FROM users WHERE email = ? LIMIT 1"

        val result =  ktormDatabase.useConnection { connection ->
            connection.prepareStatement(query).use { statement ->
                statement.setString(1, email)
                statement.executeQuery().use { resultSet ->
                    if (resultSet.next()) {
                        UserDto(
                            email = resultSet.getString("email"),
                            id = resultSet.getInt("id"),
                            role = resultSet.getString("role")
                        )
                    } else {
                        null
                    }
                }
            }
        }
        println("query lasts ${Date().time-x}ms")
        return result
    }

    override suspend fun enrolFcm(email: String, fcmId: String): Boolean {
        val affectedRows = ktormDatabase.update(DBUserTable) {
            set(it.fcmId, fcmId)
            where {
                it.email eq email
            }
        }

        return affectedRows > 0
    }

}
