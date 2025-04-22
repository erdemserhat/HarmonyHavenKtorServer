package com.erdemserhat.data.database.enneagram.enneagram_questions

import com.erdemserhat.data.database.comment.DBCommentEntity

interface EnneagramQuestionDao {
    suspend fun getQuestions(): List<DBEnneagramQuestionEntity>
}