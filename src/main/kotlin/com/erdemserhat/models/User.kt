package com.erdemserhat.models

import com.erdemserhat.dto.responses.UserInformationDto
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID


data class User(
    val id: Int,
    val name: String,
    val surname: String="-",
    val email: String,
    val password: String,
    val gender: String="-",
    val profilePhotoPath: String = "-",
    val fcmId: String = "-",
    @Contextual val uuid: String = UUID.randomUUID().toString(),
    val role: String = "user"

)



fun User.toDto(): UserInformationDto {
    return UserInformationDto(name,email)
}


@Serializable
class UserInformationSchema(
    val name: String ="",
    val surname: String ="",
    val email: String ="",
    val password: String,
    val gender: String="",
    val profilePhotoPath: String="-"
)


fun UserInformationSchema.toUser(): User {
    return User(
        id = 0,
        name = name,
        surname = surname,
        email = email,
        password = password,
        gender = gender,
        profilePhotoPath = profilePhotoPath

        )
}

