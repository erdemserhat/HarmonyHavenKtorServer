package com.erdemserhat.data.database.nosql.enneagram_famous_people

import com.erdemserhat.data.database.nosql.enneagram_question.EnneagramQuestionCategory
import com.erdemserhat.data.database.nosql.enneagram_question.EnneagramQuestionCollection
import org.bson.BsonValue
import org.bson.types.ObjectId

interface EnneagramFamousPeopleRepository {
    suspend fun findByEnneagramType(enneagramType: EnneagramType):List<EnneagramFamousPeopleCollection>?
    suspend fun insert(collection: EnneagramFamousPeopleCollection):BsonValue?
}