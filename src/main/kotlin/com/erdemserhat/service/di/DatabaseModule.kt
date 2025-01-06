package com.erdemserhat.service.di

import com.erdemserhat.service.pwrservice.PasswordResetService
import com.erdemserhat.data.repository.article.ArticleRepository
import com.erdemserhat.data.repository.article.ArticleRepositoryContract
import com.erdemserhat.data.repository.article_category.ArticleCategoryRepository
import com.erdemserhat.data.repository.article_category.ArticleCategoryRepositoryContract
import com.erdemserhat.data.repository.comments.CommentRepository
import com.erdemserhat.data.repository.comments.CommentRepositoryContract
import com.erdemserhat.data.repository.liked_comments.LikedCommentsRepository
import com.erdemserhat.data.repository.liked_comments.LikedCommentsRepositoryContract
import com.erdemserhat.data.repository.liked_quote.LikedQuoteRepository
import com.erdemserhat.data.repository.liked_quote.LikedQuoteRepositoryContract
import com.erdemserhat.data.repository.notification.NotificationRepository
import com.erdemserhat.data.repository.notification.NotificationRepositoryContract
import com.erdemserhat.data.repository.quote.QuoteRepository
import com.erdemserhat.data.repository.quote.QuoteRepositoryContract
import com.erdemserhat.data.repository.quote_category.QuoteCategoryContract
import com.erdemserhat.data.repository.quote_category.QuoteCategoryRepository
import com.erdemserhat.data.repository.user.UserRepository
import com.erdemserhat.data.repository.user.UserRepositoryContract
import com.erdemserhat.service.commentservice.CommentService
import com.erdemserhat.service.commentservice.CommentServiceContract

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
    val articleCategoryRepository: ArticleCategoryRepositoryContract by lazy {
        ArticleCategoryRepository()
    }

    /**
     * Lazily initialized singleton instance of ArticleRepositoryContract, providing access to article-related database operations.
     */
    val articleRepository: ArticleRepositoryContract by lazy {
        ArticleRepository()
    }

    val quoteRepository: QuoteRepositoryContract by lazy {
        QuoteRepository()
    }

    val quoteCategoryRepository: QuoteCategoryContract by lazy {
        QuoteCategoryRepository()
    }

    val notificationRepository: NotificationRepositoryContract by lazy {
        NotificationRepository()
    }

    val likedQuoteRepository:LikedQuoteRepositoryContract by lazy {
        LikedQuoteRepository()
    }

    val commentRepository: CommentRepositoryContract by lazy {
        CommentRepository()
    }

    val likedCommentRepository:LikedCommentsRepositoryContract by lazy {
        LikedCommentsRepository()
    }

    val commentService: CommentServiceContract by lazy {
        CommentService()
    }




    /**
     * Singleton instance of PasswordResetService, providing functionality for password reset operations.
     */
    val passwordResetService = PasswordResetService()
}