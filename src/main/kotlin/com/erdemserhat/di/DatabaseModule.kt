package com.erdemserhat.di
import com.erdemserhat.domain.password.PasswordResetService
import com.erdemserhat.repository.article.ArticleRepository
import com.erdemserhat.repository.article.ArticleRepositoryContract
import com.erdemserhat.repository.category.CategoryRepository
import com.erdemserhat.repository.category.CategoryRepositoryContract
import com.erdemserhat.repository.user.UserRepository
import com.erdemserhat.repository.user.UserRepositoryContract

object DatabaseModule{
    val userRepository: UserRepositoryContract by lazy {
        UserRepository()
    }

    val categoryRepository :CategoryRepositoryContract by lazy {
        CategoryRepository()
    }

    val articleRepository : ArticleRepositoryContract by lazy {
        ArticleRepository()
    }


    val passwordResetService = PasswordResetService()



}
