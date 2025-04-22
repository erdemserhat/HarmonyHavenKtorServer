package com.erdemserhat.data.database.enneagram.enneagram_famous_people

import com.erdemserhat.data.database.DatabaseConfig.ktormDatabase
import com.erdemserhat.data.database.article_category.DBArticleCategoryTable
import org.ktorm.dsl.eq
import org.ktorm.entity.filter
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList

class EnneagramFamousPeopleDaoImpl: EnneagramFamousPeopleDao {
    override suspend fun getFamousPeopleByEnneagramType(enneagramType: Int): List<DBEnneagramFamoutPeopleEntity> {
        val people = ktormDatabase.sequenceOf(DBEnneagramFamousPeopleTable).filter {
            it.enneagramType eq enneagramType
        }

        return people.toList()

    }
}