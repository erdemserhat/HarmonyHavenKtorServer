package com.erdemserhat.models

import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val id:Int,
    val title:String,
    val content:String,
    val publishDate:String,
    val categoryId:Int,
    val imagePath:String

)