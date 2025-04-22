package com.erdemserhat.data.database.enneagram.enneagram_test_results

import com.erdemserhat.data.database.comment.DBCommentTable.bindTo
import com.erdemserhat.data.database.comment.DBCommentTable.primaryKey
import com.erdemserhat.data.database.comment.DBCommentTable.references
import com.erdemserhat.data.database.quote.DBQuoteEntity
import com.erdemserhat.data.database.quote.DBQuoteTable
import com.erdemserhat.data.database.user.DBUserEntity
import com.erdemserhat.data.database.user.DBUserTable
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.datetime
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import java.time.LocalDateTime

object DBEnneagramTestResultsTable : Table<DBEnneagramTestResultsEntity>("enneagram_test_results") {
    val id = int("id").primaryKey().bindTo { it.id }
    val userId = int("user_id").bindTo { it.userId }
    val typeOneScore = int("type_1_score").bindTo { it.typeOneScore }
    val typeTwoScore = int("type_2_score").bindTo { it.typeTwoScore }
    val typeThreeScore = int("type_3_score").bindTo { it.typeThreeScore }
    val typeFourScore = int("type_4_score").bindTo { it.typeFourScore }
    val typeFiveScore = int("type_5_score").bindTo { it.typeFiveScore }
    val typeSixScore = int("type_6_score").bindTo { it.typeSixScore }
    val typeSevenScore = int("type_7_score").bindTo { it.typeSevenScore }
    val typeEightScore = int("type_8_score").bindTo { it.typeEightScore }
    val typeNineScore = int("type_9_score").bindTo { it.typeNineScore }
    val dominantType = int("dominant_type").bindTo { it.dominantType }
    val createdAt = datetime("created_at").bindTo { it.createdAt }
}



interface DBEnneagramTestResultsEntity : Entity<DBEnneagramTestResultsEntity> {
    companion object : Entity.Factory<DBEnneagramTestResultsEntity>()

    val id: Int
    val userId:Int
    val typeOneScore:Int
    val typeTwoScore:Int
    val typeThreeScore:Int
    val typeFourScore:Int
    val typeFiveScore:Int
    val typeSixScore:Int
    val typeSevenScore:Int
    val typeEightScore:Int
    val typeNineScore:Int
    val dominantType:Int
    val createdAt:LocalDateTime

}

@Serializable
data class EnneagramTestResultsDto(
    val id: Int ,
    val userId: Int,
    val typeOneScore: Int,
    val typeTwoScore: Int,
    val typeThreeScore: Int,
    val typeFourScore: Int,
    val typeFiveScore: Int,
    val typeSixScore: Int,
    val typeSevenScore: Int,
    val typeEightScore: Int,
    val typeNineScore: Int,
    val dominantType: Int,
    @Contextual val createdAt: LocalDateTime
)
