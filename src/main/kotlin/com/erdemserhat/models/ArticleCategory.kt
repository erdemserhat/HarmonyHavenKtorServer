package com.erdemserhat.models

import kotlinx.serialization.Serializable


@Serializable
data class ArticleCategory(
    val id:Int,
    val name:String,
    val imagePath:String
)


