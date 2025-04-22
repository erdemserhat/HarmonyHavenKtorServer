package com.erdemserhat.data.database.enneagram.enneagram_famous_people

interface EnneagramFamousPeopleDao {
    suspend fun getFamousPeopleByEnneagramType(enneagramType:Int): List<EnneagramFamousPeopleDto>

}