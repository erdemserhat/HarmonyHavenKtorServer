
package com.example.models

import io.ktor.http.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
@Serializable
    data class User(
        val id:Int,
        val name:String,
        val surname:String,
        val email:String,
        val password:String,
        val gender:String,
        val profilePhotoPath:String=""

    )