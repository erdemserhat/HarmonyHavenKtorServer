package com.example.dao

import com.example.dao.DatabaseSingleton.dbQuery
import com.example.models.Article
import com.example.models.Articles
import com.example.models.User
import com.example.models.Users
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class UserDaoFacadeImpl : UserDaoFacade {
    private fun resultRowToUser(row: ResultRow) = User(
        id = row[Users.id],
        name = row[Users.name],
        surname = row[Users.surname],
        email = row[Users.email],
        password = row[Users.password],
        gender = row[Users.gender],
        profilePhotoPath = row[Users.profilePhotoPath]
    )


    override suspend fun allUsers(): List<User> = dbQuery {
        Users.selectAll().map(::resultRowToUser)
    }

    override suspend fun user(id: Int): User? = dbQuery {
        Users
            .select { Users.id eq id }
            .map(::resultRowToUser)
            .singleOrNull()
    }


    override suspend fun addNewUser(
        name: String,
        surname: String,
        email: String,
        password: String,
        gender: String,
        profilePhoro:String,
    ): User? = dbQuery {

        val insertStatement = Users.insert {
            it[Users.name] = name
            it[Users.surname] = surname
            it[Users.email] = email
            it[Users.password] = password
            it[Users.gender] = gender
            it[Users.profilePhotoPath]=profilePhoro

        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToUser)
    }

    override suspend fun editUser(id: Int, name: String, surname: String, email: String, password: String): Boolean =
        dbQuery {
            Users.update({ Users.id eq id }) {
                it[Users.name] = name
                it[Users.surname] = surname
                it[Users.email] = email
                it[Users.password] = password

            } > 0
        }

    override suspend fun deleteUser(id: Int): Boolean = dbQuery {
        Users.deleteWhere { Users.id eq id } > 0
    }
}

val userDao: UserDaoFacade = UserDaoFacadeImpl().apply {
    runBlocking {
        if (allUsers().isEmpty()) {
            addNewUser(
                name = "Serhat",
                surname = "ERDEM",
                email = "me.serhaterdem@gmail.com",
                password = "admin",
                gender = "Male",
                profilePhoro = ""

            )
        }

    }
}