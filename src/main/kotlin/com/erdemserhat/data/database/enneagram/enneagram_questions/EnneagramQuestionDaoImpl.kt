package com.erdemserhat.data.database.enneagram.enneagram_questions

import com.erdemserhat.data.database.DatabaseConfig.ktormDatabase
import com.erdemserhat.data.database.comment.DBCommentEntity
import org.ktorm.entity.map
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList

class EnneagramQuestionDaoImpl : EnneagramQuestionDao {
    override suspend fun getQuestions(): List<EnneagramQuestionDto> {
        val questions = ktormDatabase.sequenceOf(DBEnneagramQuestionTable).map { it.toDto() }
        return questions.toList()
    }
}
