package com.erdemserhat.data.database.nosql.enneagram_famous_people

import com.erdemserhat.service.enneagram.EnneagramFamousPeopleDto


fun EnneagramFamousPeopleCollection.toDto(): EnneagramFamousPeopleDto {
    return EnneagramFamousPeopleDto(
        name = this.name,
        imageUrl = this.imageUrl,
        desc = this.desc
    )
}