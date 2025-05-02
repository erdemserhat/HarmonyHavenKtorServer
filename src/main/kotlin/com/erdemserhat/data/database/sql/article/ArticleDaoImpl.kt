package com.erdemserhat.data.database.sql.article

import com.erdemserhat.data.database.sql.MySqlDatabaseConfig
import com.erdemserhat.models.Article
import org.ktorm.dsl.*
import org.ktorm.entity.*

/**
 * Implementation of [ArticleDao] interface for interacting with the article data in the database.
 */
class ArticleDaoImpl : ArticleDao {

    /**
     * Adds a new article to the database.
     */
    override suspend fun addArticle(article: Article): Int {
        return MySqlDatabaseConfig.ktormDatabase.insert(DBArticleTable) {
            set(DBArticleTable.title, article.title)
            set(DBArticleTable.content, article.content)
            set(DBArticleTable.publishDate, article.publishDate)
            set(DBArticleTable.categoryId, article.categoryId)
            set(DBArticleTable.imagePath, article.imagePath)
        }
    }

    /**
     * Updates an existing article in the database.
     */
    override suspend fun updateArticle(articleId: Int, updatedArticle: Article): Boolean {
        return try {
            MySqlDatabaseConfig.ktormDatabase.update(DBArticleTable) {
                set(DBArticleTable.title, updatedArticle.title)
                set(DBArticleTable.content, updatedArticle.content)
                set(DBArticleTable.publishDate, updatedArticle.publishDate)
                set(DBArticleTable.categoryId, updatedArticle.categoryId)
                set(DBArticleTable.imagePath, updatedArticle.imagePath)
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
