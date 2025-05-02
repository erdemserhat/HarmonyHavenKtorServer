package com.erdemserhat.data.database.sql.enneagram.enneagram_questions

import com.erdemserhat.data.database.sql.MySqlDatabaseConfig.ktormDatabase
import org.ktorm.entity.map
import org.ktorm.entity.sequenceOf

class EnneagramQuestionDaoImpl : EnneagramQuestionDao {
    override suspend fun getQuestions(): List<EnneagramQuestionDto> {
        val questions = ktormDatabase.sequenceOf(DBEnneagramQuestionTable).map { it.toDto() }
        return questions.toList()
    }
}
