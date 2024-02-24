package com.example.models

import io.ktor.http.*

data class Category(
    val name:String,
    val icon:ContentType.Image
)