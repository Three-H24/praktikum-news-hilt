package com.example.praktikum_news_hilt.data.api

import com.example.praktikum_news_hilt.BuildConfig
import com.example.praktikum_news_hilt.data.model.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPIService {
    @GET("/v2/top-headlines")
    suspend fun getHeadlines(
        @Query("country")
        country: String,
        @Query("page")
        page: Int,
        @Query("apiKey")
        apiKey: String = BuildConfig.API_KEY
    ): Response<APIResponse>

    @GET("/v2/top-headlines")
    suspend fun getSearchQuery(
        @Query("country")
        country: String,
        @Query("page")
        page: Int,
        @Query("q")
        searhQuery: String,
        @Query("apiKey")
        apiKey: String = BuildConfig.API_KEY
    ): Response<APIResponse>
}