package com.example.praktikum_news_hilt.data.repository.datasource

import com.example.praktikum_news_hilt.data.model.APIResponse
import retrofit2.Response

interface NewsRemoteDataSource {
    suspend fun getTopHeadlines(country: String, page: Int): Response<APIResponse>
    suspend fun getSearchNews(
        country: String,
        page: Int,
        searchQuery: String
    ): Response<APIResponse>
}