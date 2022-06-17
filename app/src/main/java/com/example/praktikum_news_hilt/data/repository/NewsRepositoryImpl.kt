package com.example.praktikum_news_hilt.data.repository

import com.example.praktikum_news_hilt.data.model.APIResponse
import com.example.praktikum_news_hilt.data.model.Article
import com.example.praktikum_news_hilt.data.repository.datasource.NewsLocalDataSource
import com.example.praktikum_news_hilt.data.repository.datasource.NewsRemoteDataSource
import com.example.praktikum_news_hilt.data.util.Resource
import com.example.praktikum_news_hilt.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: NewsLocalDataSource
) : NewsRepository {

    // Function response to resource
    private fun responseToResource(response: Response<APIResponse>): Resource<APIResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    override suspend fun getNewsHeadlines(country: String, page: Int): Resource<APIResponse> {
        return responseToResource(newsRemoteDataSource.getTopHeadlines(country, page))
    }

    override suspend fun getSearchedNews(
        country: String,
        page: Int,
        seacrhQuery: String
    ): Resource<APIResponse> {
        return responseToResource(newsRemoteDataSource.getSearchNews(country, page, seacrhQuery))
    }

    override suspend fun saveNews(article: Article) {
        return newsLocalDataSource.saveArticleToDB(article)
    }

    override suspend fun deleteNews(article: Article) {
        return newsLocalDataSource.deleteArticleFromDB(article)
    }

    override fun getSavedNews(): Flow<List<Article>> {
        return newsLocalDataSource.getSavedArticles()
    }
}