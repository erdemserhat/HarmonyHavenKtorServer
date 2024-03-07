package com.example.database

import com.example.models.User
import com.example.models.UserLogin
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList
import kotlin.math.log

/**
 * Database manager class that performs database operations using the KTorm library.
 * @property ktormDatabase KTorm database object.
 */
class DatabaseManager {

    /**
     * Initializes the database connection and the KTorm database object.
     * @param hostname Database server address.
     * @param databaseName Database name.
     * @param username Database username.
     * @param password Database password.
     */

    private val hostname = "92.205.5.205"
    private val databaseName = "harmonyhaven"
    private val username = "harmonyhaven"
    private val password = "harmonyhavenadmin"

    // KTorm database object
    private val ktormDatabase: Database

    init {
        val jdbcUrl = "jdbc:mysql://$hostname:3306/$databaseName?user=$username&password=$password&useSSL=true"
        ktormDatabase = Database.connect(jdbcUrl)
    }


    /////////////     User Crud Operations   ///////////////////


    /**
     *@param userId:Pass the user id you want to show.
     * This function will return the user according to taken id parameter
     */
    fun getUserById(userId: Int): DBUserEntity? {
        return ktormDatabase.sequenceOf(DBUserTable)
            .firstOrNull { it.id eq userId }
    }

    fun getUserByLoginInformation(userLogin: UserLogin): DBUserEntity? {
        return ktormDatabase.sequenceOf(DBUserTable)
            .firstOrNull { (it.email eq userLogin.email) and (it.password eq userLogin.password) }
    }

    /**
     * Gets all users from the database.
     * @return List of users.
     */
    fun getAllUsers(): List<DBUserEntity> {
        return ktormDatabase.sequenceOf(DBUserTable).toList()
    }

    /**
     * Adds a user.
     * @param user The user to be added.
     * @return The ID of the user after being added.
     */
    fun addUser(user: User): Int {
        return ktormDatabase.insert(DBUserTable) {
            set(it.name, user.name)
            set(it.surname, user.surname)
            set(it.email, user.email)
            set(it.password, user.password)
            set(it.gender, user.gender)
            set(it.profilePhotoPath, user.profilePhotoPath)
        }

    }

    /**
     * Updates a user by ID.
     * @param userId The ID of the user to be updated.
     * @param newUser The new user information.
     * @return The updated user.
     */
    fun updateUserById(userId: Int, newUser: User): DBUserEntity? {
        ktormDatabase.update(DBUserTable) {
            set(it.name, newUser.name)
            set(it.surname, newUser.surname)
            set(it.email, newUser.email)
            set(it.password, newUser.password)
            set(it.gender, newUser.gender)
            set(it.profilePhotoPath, newUser.profilePhotoPath)
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
    fun updateUserByLoginInformation(loginInformation: UserLogin, newUser: User): DBUserEntity? {
        ktormDatabase.update(DBUserTable) {
            set(it.name, newUser.name)
            set(it.surname, newUser.surname)
            set(it.email, newUser.email)
            set(it.password, newUser.password)
            set(it.gender, newUser.gender)
            set(it.profilePhotoPath, newUser.profilePhotoPath)
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
    fun deleteUser(userId: Int): Boolean {
        val affectedRows = ktormDatabase.delete(DBUserTable) {
            it.id eq userId
        }
        return affectedRows > 0
    }

    fun controlUserExistenceByEmail(email: String): Boolean {
        val user = ktormDatabase.sequenceOf(DBUserTable)
            .firstOrNull { it.email eq email }

        if (user != null) {
            return true
        } else {
            return false
        }

    }

    fun deleteUserByLoginInformation(loginInformation: UserLogin): Boolean {
        val affectedRows = ktormDatabase.delete(DBUserTable) {
            (it.email eq loginInformation.email) and (it.password eq loginInformation.password)
        }
        return affectedRows > 0

    }

    fun controlUserExistenceByAuth(loginInformation: UserLogin): Boolean {
        val user = ktormDatabase.sequenceOf(DBUserTable)
            .firstOrNull {
                (it.email eq loginInformation.email) and (it.password eq password)
            }
        if (user != null) {
            return true
        } else {
            return false
        }
    }
}


/////////////     Article Crud Operations   ///////////////////


