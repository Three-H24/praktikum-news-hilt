package com.example.praktikum_news_hilt.data.db

import androidx.room.*
import com.example.praktikum_news_hilt.data.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Query("SELECT * FROM articles")
    fun getAllSaveArticles(): Flow<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}