package com.erdemserhat.data.database.sql.article

import com.erdemserhat.models.Article
import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

/**
 * Represents the database table for articles.
 */
object DBArticleTable : Table<DBArticleEntity>("articles") {
    // Define the columns of the articles table
    val id = int("id").primaryKey().bindTo { it.id }
    val title = varchar("title").bindTo { it.title }
    val content = varchar("content").bindTo { it.content }
    val publishDate = varchar("publish_date").bindTo { it.publishDate }
    val categoryId = int("category_id").bindTo { it.categoryId }
    val imagePath = varchar("image_path").bindTo { it.imagePath }
    val contentPreview= varchar("content_preview").bindTo { it.contentPreview }
}

/**
 * Represents an entity of an article in the database.
 */
interface DBArticleEntity : Entity<DBArticleEntity> {
    companion object : Entity.Factory<DBArticleEntity>()

    val id: Int
    val title: String
    val content: String
    val contentPreview:String
    val publishDate: String
    val categoryId: Int
    val imagePath: String
}

/**
 * Converts a DBArticleEntity object to an Article object.
 */
fun DBArticleEntity.toArticle(): Article {
    return Article(
        id = id,
        title = title,
        content = content,
        contentPreview=contentPreview,
        publishDate = publishDate,
        categoryId = categoryId,
        imagePath = imagePath
    )
}
