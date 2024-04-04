package com.erdemserhat.service.di

import com.erdemserhat.service.pwrservice.PasswordResetService
import com.erdemserhat.romote.repository.article.ArticleRepository
import com.erdemserhat.romote.repository.article.ArticleRepositoryContract
import com.erdemserhat.romote.repository.category.CategoryRepository
import com.erdemserhat.romote.repository.category.CategoryRepositoryContract
import com.erdemserhat.romote.repository.user.UserRepository
import com.erdemserhat.romote.repository.user.UserRepositoryContract

/**
 * Object responsible for managing database-related dependencies.
 */
object DatabaseModule {
    /**
     * Lazily initialized singleton instance of UserRepositoryContract, providing access to user-related database operations.
     */
    val userRepository: UserRepositoryContract by lazy {
        UserRepository()
    }

    /**
     * Lazily initialized singleton instance of CategoryRepositoryContract, providing access to category-related database operations.
     */
    val categoryRepository: CategoryRepositoryContract by lazy {
        CategoryRepository()
    }

    /**
     * Lazily initialized singleton instance of ArticleRepositoryContract, providing access to article-related database operations.
     */
    val articleRepository: ArticleRepositoryContract by lazy {
        ArticleRepository()
    }

    /**
     * Singleton instance of PasswordResetService, providing functionality for password reset operations.
     */
    val passwordResetService = PasswordResetService()
}
