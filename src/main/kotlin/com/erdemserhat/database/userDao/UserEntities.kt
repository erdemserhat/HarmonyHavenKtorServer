package com.erdemserhat.database.userDao

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object DBUserTable : Table<DBUserEntity>("User") {
    val id = int("id").primaryKey().bindTo {it.id}
    val name = varchar("name").bindTo {it.name }
    val surname = varchar("surname").bindTo {it.surname}
    val email = varchar("email").bindTo {it.email }
    val password = varchar("password").bindTo { it.password}
    val gender = varchar("gender").bindTo { it.gender}
    val profilePhotoPath = varchar("profilePhotoPath").bindTo { it.profilePhotoPath }
}

interface DBUserEntity : Entity<DBUserEntity> {
    companion object : Entity.Factory<DBUserEntity>()

    val id: Int
    val name:String
    val surname:String
    val email:String
    val password:String
    val gender:String
    val profilePhotoPath:String

}