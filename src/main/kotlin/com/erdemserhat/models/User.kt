
package com.erdemserhat.models

import kotlinx.serialization.Serializable

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