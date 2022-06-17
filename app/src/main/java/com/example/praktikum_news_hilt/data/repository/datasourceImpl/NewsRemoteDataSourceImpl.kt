package com.example.praktikum_news_hilt.data.repository.datasourceImpl

import com.example.praktikum_news_hilt.data.api.NewsAPIService
import com.example.praktikum_news_hilt.data.model.APIResponse
import com.example.praktikum_news_hilt.data.repository.datasource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
    private val newsAPIService: NewsAPIService
) : NewsRemoteDataSource {
    override suspend fun getTopHeadlines(country: String, page: Int): Response<APIResponse> {
        return newsAPIService.getHeadlines(country, page)
    }

    override suspend fun getSearchNews(
        country: String,
        page: Int,
        searchQuery: String
    ): Response<APIResponse> {
        return newsAPIService.getSearchQuery(country, page, searchQuery)
    }
}