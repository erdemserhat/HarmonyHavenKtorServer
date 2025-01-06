package com.erdemserhat.dto.responses

import kotlinx.serialization.Serializable

@Serializable
data class CommentsClientDto(
    val id:Int,
    val date:String,
    val author:String,
    val content:String,
    val likeCount:Int,
    val isLiked:Boolean,
    val authorProfilePictureUrl:String,
    val hasOwnership:Boolean
)
