package com.erdemserhat.models

import io.ktor.http.*
import kotlinx.serialization.Serializable


@Serializable
data class Category(
    val id:Int,
    val name:String,
    val imagePath:String

)