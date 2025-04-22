package com.erdemserhat.data.database.enneagram.enneagram_questions

import com.erdemserhat.data.database.enneagram.enneagram_answers.EnneagramAnswerDao
import com.erdemserhat.data.database.DatabaseConfig.ktormDatabase
import com.erdemserhat.data.database.comment.DBCommentEntity
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList

class EnneagramQuestionDaoImpl: EnneagramQuestionDao {
    override suspend fun getQuestions(): List<DBEnneagramQuestionEntity> {
        return ktormDatabase.sequenceOf(DBEnneagramQuestionTable).toList()
    }
}