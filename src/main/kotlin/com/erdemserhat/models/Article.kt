package com.erdemserhat.models

import io.ktor.http.*
import org.jetbrains.exposed.sql.Table

data class Article(
    val title: String,
    val body: String,
    val category: String,
    val image: ContentType.Image,
    val id: Int
)

object Articles : Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title",128)
    val body = varchar("body",2048)
    val category = varchar("category",128)
    val imagePath = varchar("image_path",255)

    override val primaryKey = PrimaryKey(id)




}