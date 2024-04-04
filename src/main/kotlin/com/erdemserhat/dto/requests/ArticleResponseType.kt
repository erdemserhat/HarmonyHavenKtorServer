package com.erdemserhat.dto.requests

import kotlinx.serialization.Serializable

@Serializable
data class ArticleResponseType(
    val id:Int,
    val title:String,
    val content:String,
    val publishDate:String,
    val category:String?,
    val imagePath:String

)

