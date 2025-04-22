package com.erdemserhat.data.database.enneagram.enneagram_test_results

import kotlinx.serialization.Contextual
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.datetime
import org.ktorm.schema.int
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Local

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
data class EnneagramTestResultDto(
    val id: Int=-1,
    val userId: Int=0,
    val typeOneScore: Int=1,
    val typeTwoScore: Int=2,
    val typeThreeScore: Int=3,
    val typeFourScore: Int=4,
    val typeFiveScore: Int=5,
    val typeSixScore: Int=6,
    val typeSevenScore: Int=7,
    val typeEightScore: Int=8,
    val typeNineScore: Int=9,
    val dominantType: Int =10,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime = LocalDateTime.now(),
)

fun DBEnneagramTestResultsEntity.toDto(): EnneagramTestResultDto {
    return EnneagramTestResultDto(
        id = this.id,
        userId = this.userId,
        typeOneScore = this.typeOneScore,
        typeTwoScore = this.typeTwoScore,
        typeThreeScore = this.typeThreeScore,
        typeFourScore = this.typeFourScore,
        typeFiveScore = this.typeFiveScore,
        typeSixScore = this.typeSixScore,
        typeSevenScore = this.typeSevenScore,
        typeEightScore = this.typeEightScore,
        typeNineScore = this.typeNineScore,
        dominantType = this.dominantType,
        createdAt = createdAt
    )

}

object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeString(value.format(formatter))
    }

    override fun deserialize(decoder: Decoder): LocalDateTime {
        return LocalDateTime.parse(decoder.decodeString(), formatter)
    }
}
