package com.erdemserhat.data.database.sql.enneagram.enneagram_questions

import com.erdemserhat.data.database.nosql.enneagram_question.EnneagramQuestionCategory
import com.erdemserhat.data.database.nosql.enneagram_question.EnneagramQuestionCollection
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor


object DBEnneagramQuestionTable : Table<DBEnneagramQuestionEntity>("enneagram_questions") {
    val id = int("id").primaryKey().bindTo { it.id }
    val personalityNumber = int("personality_number").bindTo { it.personalityNumber}
    val content = varchar("content").bindTo { it.content }
}

interface DBEnneagramQuestionEntity : Entity<DBEnneagramQuestionEntity> {
    companion object : Entity.Factory<DBEnneagramQuestionEntity>()
    val id: Int
    val personalityNumber: Int // Linked to the `authorId` foreign key
    val content: String  // Linked to the `postId` foreign key
}


fun DBEnneagramQuestionEntity.toDto(): EnneagramQuestionDto {
    return EnneagramQuestionDto(
        personalityNumber =this.personalityNumber,
        content =this.content,
    )


}

@Serializable
data class EnneagramQuestionDto(
    @Contextual val id: ObjectId = ObjectId(),
    val personalityNumber: Int,
    val content: String,
)

fun EnneagramQuestionDto.toEnneagramQuestionCollection():EnneagramQuestionCollection{
    return EnneagramQuestionCollection(
        personalityNumber = personalityNumber,
        content = content,
        enneagramQuestionCategory = EnneagramQuestionCategory.BASIC
    )
}

