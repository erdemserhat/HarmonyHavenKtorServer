package com.erdemserhat.data.database.sql.enneagram.enneagram_answers

interface EnneagramAnswerDao {
    suspend fun addAnswers(answers:List<EnneagramAnswerDto>, userId: Int)
    suspend fun getAnswersByUserId(userId:Int):List<EnneagramAnswerDto>
    suspend fun deleteAnswersByUserId(userId:Int)
}

