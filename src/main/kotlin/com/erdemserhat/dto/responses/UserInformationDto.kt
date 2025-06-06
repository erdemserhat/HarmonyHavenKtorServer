package com.erdemserhat.dto.responses

import kotlinx.serialization.Serializable

@Serializable
data class UserInformationDto(
    val name:String,
    val email:String,
    val profilePhotoPath:String,
    val id:Int,
   val  activeDays:Int
)
