package com.erdemserhat.data.database.nosql.enneagram_question

import com.erdemserhat.data.database.sql.enneagram.enneagram_questions.EnneagramQuestionDto

fun EnneagramQuestionCollection.toDto(): EnneagramQuestionDto {
    return EnneagramQuestionDto(
        id = this.id,
        personalityNumber = this.personalityNumber,
        content = this.content,)
}