
package com.example.models

import io.ktor.http.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
@Serializable
data class User(
    val name:String,
    val surname:String,
    val email:String,
    val password:String,
    val gender:String,
    val profilePhotoPath:String="",
    val id:Int
)

object Users :Table(){
    val id=integer("id").autoIncrement()
    val name=varchar("name",128)
    val surname=varchar("surname",128)
    val email = varchar("email",128)
    val password=varchar("password",128)
    val gender=varchar("gender",32)
    val profilePhotoPath=varchar("profile_photo",255)

}
