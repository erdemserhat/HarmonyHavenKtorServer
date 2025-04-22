package com.erdemserhat.data.database.enneagram.enneagram_famous_people

import com.erdemserhat.data.database.DatabaseConfig.ktormDatabase
import com.erdemserhat.data.database.article_category.DBArticleCategoryTable
import org.ktorm.dsl.eq
import org.ktorm.entity.*

class EnneagramFamousPeopleDaoImpl : EnneagramFamousPeopleDao {
    override suspend fun getFamousPeopleByEnneagramType(enneagramType: Int): List<EnneagramFamousPeopleDto> {
        val people = ktormDatabase.sequenceOf(DBEnneagramFamousPeopleTable).filter {
            it.enneagramType eq enneagramType
        }.map { entity ->
            // Convert DB entity to DTO
            entity.toDto()
        }

        return people.toList() // Convert to list of DTOs
    }
}
