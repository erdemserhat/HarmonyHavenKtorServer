package com.erdemserhat.romote.database.notification

import com.erdemserhat.romote.database.user.DBUserEntity
import org.apache.commons.net.ntp.TimeStamp
import org.ktorm.entity.Entity
import org.ktorm.schema.*
import java.time.Instant

object DBNotificationTable : Table<DBNotificationEntity>("notifications") {
    // Define the columns of the table
    val id = int("id").primaryKey().bindTo { it.id }
    val user_id = int("user_id").bindTo { it.userId }
    val title = text("title").bindTo { it.title }
    val content = text("content").bindTo { it.content }
    val is_read = boolean("is_read").bindTo { it.isRead }
    val timeStamp = long("timestamp").bindTo { it.timeStamp }
}

/**
 * Represents a category entity in the database.
 */
interface DBNotificationEntity : Entity<DBNotificationEntity> {
    companion object : Entity.Factory<DBUserEntity>()
    val id:Int
    val userId:Int
    val title:String
    val content:String
    val isRead:Boolean
    val timeStamp:Long
}
