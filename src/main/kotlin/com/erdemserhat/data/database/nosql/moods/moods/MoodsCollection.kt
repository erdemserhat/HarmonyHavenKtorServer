package com.erdemserhat.data.database.nosql.moods.moods

import com.erdemserhat.data.database.nosql.notification_preferences.NotificationDefinedType
import com.erdemserhat.data.database.nosql.notification_preferences.NotificationType
import com.erdemserhat.data.database.nosql.notification_preferences.PredefinedMessageSubject
import com.erdemserhat.data.database.nosql.notification_preferences.PredefinedReminderSubject
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

@Serializable
data class MoodsCollection(
    @BsonId @Contextual val id: ObjectId = ObjectId(),
    val name: String,
    val moodImagePath: String
)
