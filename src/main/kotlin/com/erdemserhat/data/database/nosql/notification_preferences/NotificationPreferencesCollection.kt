package com.erdemserhat.data.database.nosql.notification_preferences

import kotlinx.serialization.Contextual
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class NotificationPreferencesCollection(
    @BsonId val id: ObjectId = ObjectId(),
    val definedType:NotificationDefinedType,
    val type: NotificationType,
    val customSubject:String? = null,
    val predefinedMessageSubject:PredefinedMessageSubject? = null,
    val predefinedReminderSubject: PredefinedReminderSubject? = null,
    val userId: Int,
    val preferredTime: LocalTime,
    val daysOfWeek: List<DayOfWeek>, // cannot be null
    val lastSentAt: LocalDateTime? = null

){
    init {
        require(daysOfWeek.isNotEmpty()) { "At least one day must be selected for notifications." }
    }
}


enum class PredefinedMessageSubject{
    GOOD_MORNING,
    GOOD_EVENING,
    MOTIVATION,
}

enum class PredefinedReminderSubject(
){
    WATER_DRINK,
    SLEEP_TIME,
    EXERCISE
}

enum class NotificationDefinedType{
    CUSTOM,
    DEFAULT
}

enum class NotificationType{
    REMINDER,
    MESSAGE
}

@Serializable
data class NotificationSchedulerDto(
    @Contextual
    val id: ObjectId? = null,
    val definedType: NotificationDefinedType,
    val type: NotificationType,
    val customSubject:String? = null,
    val predefinedMessageSubject: PredefinedMessageSubject? = null,
    val predefinedReminderSubject: PredefinedReminderSubject? = null,
    @Serializable(with = LocalTimeSerializer::class)
    val preferredTime: LocalTime,
    val daysOfWeek: List<DayOfWeek>
)

fun NotificationSchedulerDto.toCollection(userId:Int): NotificationPreferencesCollection{
    return NotificationPreferencesCollection(
        id = this.id ?: ObjectId(),
        definedType = this.definedType,
        type = this.type,
        customSubject = this.customSubject,
        predefinedReminderSubject = this.predefinedReminderSubject,
        predefinedMessageSubject = this.predefinedMessageSubject,
        userId = userId,
        preferredTime = this.preferredTime,
        daysOfWeek = this.daysOfWeek
    )
}

fun NotificationPreferencesCollection.toDto():NotificationSchedulerDto{
    return NotificationSchedulerDto(
        id = this.id,
        definedType = this.definedType,
        type = this.type,
        customSubject = this.customSubject,
        predefinedReminderSubject = this.predefinedReminderSubject,
        predefinedMessageSubject = this.predefinedMessageSubject,
        preferredTime = this.preferredTime,
        daysOfWeek = this.daysOfWeek
    )

}


object LocalTimeSerializer : KSerializer<LocalTime> {
    private val formatter = DateTimeFormatter.ISO_LOCAL_TIME

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalTime) {
        encoder.encodeString(value.format(formatter))
    }

    override fun deserialize(decoder: Decoder): LocalTime {
        return LocalTime.parse(decoder.decodeString(), formatter)
    }
}



