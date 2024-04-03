package com.erdemserhat.database.userDao

import com.erdemserhat.database.DatabaseConfig.ktormDatabase
import com.erdemserhat.models.User
import com.erdemserhat.models.rest.client.UserAuthenticationRequest
import org.ktorm.dsl.*
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList
import java.util.*

/**
 * Database manager class that performs database operations using the KTorm library.
 * @property ktormDatabase KTorm database object.
 */

class UserDaoImpl() : UserDao {


    /////////////  User Model Database Operations   ///////////////////


    /**
     *@param userId:Pass the user id you want to show.
     * This function will return the user according to taken id parameter
     */
    override fun getUserById(userId: Int): DBUserEntity? {
        return ktormDatabase.sequenceOf(DBUserTable)
            .firstOrNull { it.id eq userId }
    }

    override fun getUserByLoginInformation(userLogin: UserAuthenticationRequest): DBUserEntity? {
        return ktormDatabase.sequenceOf(DBUserTable)
            .firstOrNull { (it.email eq userLogin.email) and (it.password eq userLogin.password) }
    }

    /**
     * Gets all users from the database.
     * @return List of users.
     */
    override fun getAllUsers(): List<DBUserEntity> {
        return ktormDatabase.sequenceOf(DBUserTable).toList()
    }

    /**
     * Adds a user.
     * @param user The user to be added.
     * @return The ID of the user after being added.
     */
    override fun addUser(user: User): Int {
        return ktormDatabase.insert(DBUserTable) {
            set(it.name, user.name)
            set(it.surname, user.surname)
            set(it.email, user.email)
            set(it.password, user.password)
            set(it.gender, user.gender)
            set(it.profilePhotoPath, user.profilePhotoPath)
            set(it.fcmId, user.fcmId)
            set(it.uuid, user.uuid)
            set(it.role, user.role)
        }

    }

    /**
     * Updates a user by ID.
     * @param userId The ID of the user to be updated.
     * @param newUser The new user information.
     * @return The updated user.
     */
    override fun updateUserById(userId: Int, newUser: User): DBUserEntity? {
        ktormDatabase.update(DBUserTable) {
            set(it.name, newUser.name)
            set(it.surname, newUser.surname)
            set(it.email, newUser.email)
            set(it.password, newUser.password)
            set(it.gender, newUser.gender)
            set(it.profilePhotoPath, newUser.profilePhotoPath)
            set(it.fcmId, newUser.fcmId)
            set(it.uuid, newUser.uuid)
            set(it.role, newUser.role)
            where {
                it.id eq userId
            }
        }
        return ktormDatabase.sequenceOf(DBUserTable)
            .firstOrNull { it.id eq userId }
    }


    /**
     * Updates a user by login information.
     * @param login The user login information.
     * @param newUser The new user information.
     */
    override fun updateUserByLoginInformation(loginInformation: UserAuthenticationRequest, newUser: User): DBUserEntity? {
        ktormDatabase.update(DBUserTable) {
            set(it.name, newUser.name)
            set(it.surname, newUser.surname)
            set(it.email, newUser.email)
            set(it.password, newUser.password)
            set(it.gender, newUser.gender)
            set(it.profilePhotoPath, newUser.profilePhotoPath)
            set(it.fcmId, newUser.fcmId)
            set(it.uuid, newUser.uuid)
            set(it.role, newUser.role)

            where {
                (it.email eq loginInformation.email) and (it.password eq loginInformation.password)
            }
        }
        return ktormDatabase.sequenceOf(DBUserTable)
            .firstOrNull { (it.email eq loginInformation.email) and (it.password eq loginInformation.password) }

    }


    /**
     * Deletes a user by ID.
     * @param userId The ID of the user to be deleted.
     * @return Whether the user has been successfully deleted or not.
     */
    override fun deleteUser(userId: Int): Boolean {
        val affectedRows = ktormDatabase.delete(DBUserTable) {
            it.id eq userId
        }
        return affectedRows > 0
    }

    override fun controlUserExistenceByEmail(email: String): Boolean {
        val user = ktormDatabase.sequenceOf(DBUserTable)
            .firstOrNull { it.email eq email }

        if (user != null) {
            return true
        } else {
            return false
        }

    }

    override fun deleteUserByEmailInformation(email:String): Boolean {
        val affectedRows = ktormDatabase.delete(DBUserTable) {
            (it.email eq email)
        }
        return affectedRows > 0

    }

    override fun controlUserExistenceByAuth(loginInformation: UserAuthenticationRequest): Boolean {
        val user = ktormDatabase.sequenceOf(DBUserTable)
            .firstOrNull {
                (it.email eq loginInformation.email) and (it.password eq loginInformation.password)
            }
        if (user != null) {
            return true
        } else {
            return false
        }
    }

    override fun updateUserPasswordByEmail(email: String, newPassword: String): DBUserEntity? {
        ktormDatabase.update(DBUserTable){
            set(it.password,newPassword)
            where {
                it.email eq email
            }
        }
        return ktormDatabase.sequenceOf(DBUserTable)
            .firstOrNull { it.email eq email }

    }

    override fun getUserByEmail(email: String): DBUserEntity? {
        return ktormDatabase.sequenceOf(DBUserTable)
            .firstOrNull{
                it.email eq email
            }


    }

}

