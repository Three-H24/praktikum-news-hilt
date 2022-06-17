package com.example.praktikum_news_hilt.data.repository.datasourceImpl

import com.example.praktikum_news_hilt.data.db.ArticleDAO
import com.example.praktikum_news_hilt.data.model.Article
import com.example.praktikum_news_hilt.data.repository.datasource.NewsLocalDataSource
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSourceImpl(
    private val articleDAO: ArticleDAO
) : NewsLocalDataSource {
    override suspend fun saveArticleToDB(article: Article) {
        articleDAO.insert(article)
    }

    override fun getSavedArticles(): Flow<List<Article>> {
        return articleDAO.getAllSaveArticles()
    }

    override suspend fun deleteArticleFromDB(article: Article) {
        articleDAO.deleteArticle(article)
    }
}