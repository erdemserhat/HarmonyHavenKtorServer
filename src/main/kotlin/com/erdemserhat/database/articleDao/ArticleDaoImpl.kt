package com.erdemserhat.database.articleDao

import com.erdemserhat.database.DatabaseConfig
import com.erdemserhat.database.DatabaseConfig.ktormDatabase
import com.erdemserhat.database.categoryDao.DBCategoryEntity
import com.erdemserhat.database.categoryDao.DBCategoryTable
import com.erdemserhat.models.Article
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.insert
import org.ktorm.dsl.update
import org.ktorm.entity.*

//id	title	content	publish_date	category_id	image_path

class ArticleDaoImpl() : ArticleDao {
    override fun addArticle(article: Article): Int {
        return ktormDatabase.insert(DBArticleTable){
            set(it.title,article.title)
            set(it.content,article.content)
            set(it.publishDate,article.publishDate)
            set(it.categoryId,article.categoryId)
            set(it.imagePath,article.imagePath)

        }
    }

    override fun updateArticle(articleId: Int, updatedArticle: Article): Boolean {


        try {
            ktormDatabase.update(DBArticleTable){
                set(it.title,updatedArticle.title)
                set(it.content,updatedArticle.content)
                set(it.publishDate,updatedArticle.publishDate)
                set(it.categoryId,updatedArticle.categoryId)
                set(it.imagePath,updatedArticle.imagePath)

                where {
                    it.id eq articleId
                }
            }
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override fun deleteArticle(articleId: Int): Boolean {
        val affectedRows = ktormDatabase.delete(DBArticleTable) {
            it.id eq articleId
        }
        return affectedRows > 0
    }

    override suspend fun getArticle(articleId: Int): DBArticleEntity? {
        return ktormDatabase.sequenceOf(DBArticleTable)
            .firstOrNull{it.id eq articleId}
    }

    override fun getAllArticles(): List<DBArticleEntity> {
        return ktormDatabase.sequenceOf(DBArticleTable).toList()
    }

    override fun getArticlesByCategory(categoryId: Int): List<DBArticleEntity> {
        return ktormDatabase.sequenceOf(DBArticleTable)
            .filter { it.categoryId eq categoryId }.toList()
    }
}
