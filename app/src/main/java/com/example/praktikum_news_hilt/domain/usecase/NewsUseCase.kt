package com.example.praktikum_news_hilt.domain.usecase

import com.example.praktikum_news_hilt.data.model.APIResponse
import com.example.praktikum_news_hilt.data.model.Article
import com.example.praktikum_news_hilt.data.util.Resource
import com.example.praktikum_news_hilt.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetNewsHeadlinesUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(country: String, page: Int): Resource<APIResponse> {
        return newsRepository.getNewsHeadlines(country, page)
    }
}

class SaveNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(article: Article) = newsRepository.saveNews(article)
}

class GetSavedNewsUseCase(private val newsRepository: NewsRepository) {
    fun execute(): Flow<List<Article>> {
        return newsRepository.getSavedNews()
    }
}

class DeleteSavedNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(article: Article) = newsRepository.deleteNews(article)
}

class GetSearchNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(country: String, page: Int, querySearch: String): Resource<APIResponse> {
        return newsRepository.getSearchedNews(country, page, querySearch)
    }
}
