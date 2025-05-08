package com.erdemserhat.data.database.nosql.notification_preferences

import com.erdemserhat.data.database.nosql.enneagram_type_descriptions.EnneagramTypeDescriptionCategory
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

data class NotificationPreferencesCollection(
    @BsonId val id: ObjectId = ObjectId(),
    val notificationType:NotificationType,
    val preferredNotificationCustomSubject:String?,
    val preferredNotificationPredefinedSubject:PredefinedSubject?,
    val userId: Int,
    val preferredTime: LocalTime,
    val daysOfWeek: List<DayOfWeek> // cannot be null
){
    init {
        require(daysOfWeek.isNotEmpty()) { "At least one day must be selected for notifications." }
    }
}


enum class PredefinedSubject(
){
    GOOD_MORNING,
    GOOD_EVENING,
    MOTIVATION
    //...
}

enum class NotificationType{
    CUSTOM,
    DEFAULT
}




