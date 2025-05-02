package com.erdemserhat.data.database.sql.enneagram.enneagram_questions

interface EnneagramQuestionDao {
    suspend fun getQuestions(): List<EnneagramQuestionDto>
}