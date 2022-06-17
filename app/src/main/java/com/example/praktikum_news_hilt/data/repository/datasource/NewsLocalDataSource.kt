package com.example.praktikum_news_hilt.data.repository.datasource

import com.example.praktikum_news_hilt.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {
    suspend fun saveArticleToDB(article: Article)

    fun getSavedArticles(): Flow<List<Article>>

    suspend fun deleteArticleFromDB(article: Article)
}