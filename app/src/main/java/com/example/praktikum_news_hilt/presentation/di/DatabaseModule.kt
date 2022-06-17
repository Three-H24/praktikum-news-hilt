package com.example.praktikum_news_hilt.presentation.di

import android.app.Application
import androidx.room.Room
import com.example.praktikum_news_hilt.data.db.ArticleDAO
import com.example.praktikum_news_hilt.data.db.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideNewsDatabase(app: Application): ArticleDatabase {
        return Room.databaseBuilder(app, ArticleDatabase::class.java, "news_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideArticelDAO(articleDatabase: ArticleDatabase): ArticleDAO {
        return articleDatabase.getArticleDAO()
    }
}