package com.erdemserhat.dto.responses

import java.time.LocalDateTime


data class CommentBaseDto(
    val id:Int,
    val authorId:Int,
    val postId:Int,
    val authorName:String,
    val authorProfilePic:String,
    val content:String,
    val date: LocalDateTime
)
