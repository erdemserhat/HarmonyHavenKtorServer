package com.erdemserhat.data.database.sql.article

import com.erdemserhat.data.database.sql.MySqlDatabaseConfig
import com.erdemserhat.models.ArticleDto
import org.ktorm.dsl.*
import org.ktorm.entity.*

/**
 * Implementation of [ArticleDao] interface for interacting with the article data in the database.
 */
class ArticleDaoImpl : ArticleDao {

    /**
     * Adds a new article to the database.
     */
    override suspend fun addArticle(articleDto: ArticleDto): Int {
        return MySqlDatabaseConfig.ktormDatabase.insert(DBArticleTable) {
            set(DBArticleTable.title, articleDto.title)
            set(DBArticleTable.content, articleDto.content)
            set(DBArticleTable.publishDate, articleDto.publishDate)
            set(DBArticleTable.categoryId, articleDto.categoryId)
            set(DBArticleTable.imagePath, articleDto.imagePath)
        }
    }

    /**
     * Updates an existing article in the database.
     */
    override suspend fun updateArticle(articleId: Int, updatedArticleDto: ArticleDto): Boolean {
        return try {
            MySqlDatabaseConfig.ktormDatabase.update(DBArticleTable) {
                set(DBArticleTable.title, updatedArticleDto.title)
                set(DBArticleTable.content, updatedArticleDto.content)
                set(DBArticleTable.publishDate, updatedArticleDto.publishDate)
                set(DBArticleTable.categoryId, updatedArticleDto.categoryId)
                set(DBArticleTable.imagePath, updatedArticleDto.imagePath)
                where {
                    DBArticleTable.id eq articleId
                }
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Deletes an article from the database.
     */
    override suspend fun deleteArticle(articleId: Int): Boolean {
        val affectedRows = MySqlDatabaseConfig.ktormDatabase.delete(DBArticleTable) {
            DBArticleTable.id eq articleId
        }
        return affectedRows > 0
    }

    /**
     * Retrieves an article from the database by its ID.
     */
    override suspend fun getArticle(articleId: Int): DBArticleEntity? {
        return MySqlDatabaseConfig.ktormDatabase.sequenceOf(DBArticleTable)
            .firstOrNull { DBArticleTable.id eq articleId }
    }

    /**
     * Retrieves all articles from the database.
     */
    override suspend fun getAllArticles(): List<DBArticleEntity> {
        return MySqlDatabaseConfig.ktormDatabase.sequenceOf(DBArticleTable).toList()
    }

    /**
     * Retrieves articles belonging to a specific category from the database.
     */
    override suspend fun getArticlesByCategory(categoryId: Int): List<DBArticleEntity> {
        return MySqlDatabaseConfig.ktormDatabase.sequenceOf(DBArticleTable)
            .filter { DBArticleTable.categoryId eq categoryId }.toList()
    }


}
