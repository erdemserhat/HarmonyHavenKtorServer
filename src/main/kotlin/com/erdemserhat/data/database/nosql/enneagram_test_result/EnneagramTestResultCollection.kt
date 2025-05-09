package com.erdemserhat.data.database.nosql.enneagram_test_result

import com.erdemserhat.data.database.sql.enneagram.enneagram_answers.EnneagramAnswerDto
import com.erdemserhat.service.enneagram.EnneagramScore
import com.erdemserhat.service.enneagram.EnneagramTestResult
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.LocalDateTime
import java.util.Date

data class EnneagramTestResultCollection(
    @BsonId val id: ObjectId = ObjectId(),
    val userId:Int,
    val typeScores: List<EnneagramScore>,

    val answers:List<EnneagramAnswerDto>,
    val dominantType: EnneagramScore,
    val wingTypes: EnneagramWingTypes,
    val testCategory:EnneagramTestResultCategory,
    val createdAt:LocalDateTime = LocalDateTime.now()
)

fun EnneagramTestResultCollection.toEnneagramTestResult(): EnneagramTestResult {

    return EnneagramTestResult(
        typeScores = this.typeScores,
        dominantType = this.dominantType,
        wingType = this.wingTypes,

    )


}


