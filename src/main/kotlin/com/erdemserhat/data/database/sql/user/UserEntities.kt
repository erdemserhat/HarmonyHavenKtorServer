package com.erdemserhat.data.database.sql.user

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

/**
 * Table object representing the "users" table in the database.
 */
object DBUserTable : Table<DBUserEntity>("users") {
    val id = int("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
    val surname = varchar("surname").bindTo { it.surname }
    val email = varchar("email").bindTo { it.email }
    val password = varchar("password").bindTo { it.password }
    val gender = varchar("gender").bindTo { it.gender }
    val profilePhotoPath = varchar("profilePhotoPath").bindTo { it.profilePhotoPath }
    val fcmId = varchar("fcm_id").bindTo { it.fcmId }
    val uuid = varchar("uuid").bindTo { it.uuid }
    val role = varchar("role").bindTo { it.role }
}

/**
 * Entity interface representing a user entity.
 */
interface DBUserEntity : Entity<DBUserEntity> {
    companion object : Entity.Factory<DBUserEntity>()
    val id: Int
    val name: String
    val surname: String
    val email: String
    val password: String
    val gender: String
    val profilePhotoPath: String
    val fcmId: String
    val uuid: String
    val role: String
}

data class UserDto(
    val id: Int,
    val email: String,
    val role: String,
    val name: String,
)
