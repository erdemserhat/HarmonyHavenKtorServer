package com.erdemserhat.models

import io.ktor.http.*

data class Category(
    val name:String,
    val icon:ContentType.Image
)