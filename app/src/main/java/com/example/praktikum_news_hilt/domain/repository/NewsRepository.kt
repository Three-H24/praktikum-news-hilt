package com.example.praktikum_news_hilt.domain.repository

import com.example.praktikum_news_hilt.data.model.APIResponse
import com.example.praktikum_news_hilt.data.model.Article
import com.example.praktikum_news_hilt.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNewsHeadlines(country: String, page: Int): Resource<APIResponse>

    suspend fun getSearchedNews(
        country: String,
        page: Int,
        seacrhQuery: String
    ): Resource<APIResponse>

    suspend fun saveNews(article: Article)

    suspend fun deleteNews(article: Article)

    fun getSavedNews(): Flow<List<Article>>
}