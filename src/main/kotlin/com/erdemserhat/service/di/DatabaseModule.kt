package com.erdemserhat.service.di

import com.erdemserhat.data.database.nosql.enneagram_chart.EnneagramChartRepository
import com.erdemserhat.data.database.nosql.enneagram_chart.EnneagramChartRepositoryImpl
import com.erdemserhat.data.database.nosql.enneagram_extra_type_description.EnneagramExtraTypeDescriptionRepository
import com.erdemserhat.data.database.nosql.enneagram_extra_type_description.EnneagramExtraTypeDescriptionRepositoryImpl
import com.erdemserhat.data.database.nosql.enneagram_famous_people.EnneagramFamousPeopleRepository
import com.erdemserhat.data.database.nosql.enneagram_famous_people.EnneagramFamousPeopleRepositoryImpl
import com.erdemserhat.data.database.nosql.enneagram_question.EnneagramQuestionRepository
import com.erdemserhat.data.database.nosql.enneagram_question.EnneagramQuestionRepositoryImpl
import com.erdemserhat.data.database.nosql.enneagram_test_result.EnneagramTestResultRepository
import com.erdemserhat.data.database.nosql.enneagram_test_result.EnneagramTestResultRepositoryImpl
import com.erdemserhat.data.database.nosql.enneagram_type_descriptions.EnneagramTypeDescriptionRepository
import com.erdemserhat.data.database.nosql.enneagram_type_descriptions.EnneagramTypeDescriptionRepositoryImpl
import com.erdemserhat.data.database.nosql.moods.moods.MoodsRepository
import com.erdemserhat.data.database.nosql.moods.moods.MoodsRepositoryImpl
import com.erdemserhat.data.database.nosql.moods.user_moods.UserMoodsRepository
import com.erdemserhat.data.database.nosql.moods.user_moods.UserMoodsRepositoryImpl
import com.erdemserhat.data.database.nosql.notification_preferences.NotificationPreferencesRepository
import com.erdemserhat.data.database.nosql.notification_preferences.NotificationPreferencesRepositoryImpl
import com.erdemserhat.data.database.sql.enneagram.enneagram_answers.EnneagramAnswerDao
import com.erdemserhat.data.database.sql.enneagram.enneagram_questions.EnneagramQuestionDao
import com.erdemserhat.data.database.sql.enneagram.enneagram_questions.EnneagramQuestionDaoImpl
import com.erdemserhat.data.database.sql.enneagram.enneagram_test_results.EnneagramTestResultDao
import com.erdemserhat.data.database.sql.enneagram.enneagram_test_results.EnneagramTestResultDaoImpl
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


    val notificationPreferencesRepository: NotificationPreferencesRepository by lazy {
        NotificationPreferencesRepositoryImpl()
    }

    val moodsRepository : MoodsRepository by lazy {
        MoodsRepositoryImpl()
    }

    val userMoodsRepository:UserMoodsRepository by lazy {
        UserMoodsRepositoryImpl()
    }


}

object EnneagramRepositoryModule{



    val enneagramQuestionRepository: EnneagramQuestionRepositoryImpl by lazy {
        EnneagramQuestionRepositoryImpl()
    }

    val enneagramFamousPeopleRepository: EnneagramFamousPeopleRepository by lazy {
        EnneagramFamousPeopleRepositoryImpl()
    }


    val enneagramChartRepository: EnneagramChartRepository by lazy {
        EnneagramChartRepositoryImpl()
    }

    val enneagramTypeDescriptionRepository: EnneagramTypeDescriptionRepository by lazy{
        EnneagramTypeDescriptionRepositoryImpl()
    }

    val enneagramExtraTypeDescriptionRepository: EnneagramExtraTypeDescriptionRepository by lazy{
        EnneagramExtraTypeDescriptionRepositoryImpl()
    }

    val enneagramTestResultRepository: EnneagramTestResultRepository by lazy {
        EnneagramTestResultRepositoryImpl()
    }

}