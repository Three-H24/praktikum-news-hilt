package com.example.praktikum_news_hilt.presentation.di

import com.example.praktikum_news_hilt.data.db.ArticleDAO
import com.example.praktikum_news_hilt.data.repository.datasource.NewsLocalDataSource
import com.example.praktikum_news_hilt.data.repository.datasourceImpl.NewsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class LocalDataModule {
    @Singleton
    @Provides
    fun provideNewsLocalDataSource(articleDAO: ArticleDAO): NewsLocalDataSource {
        return NewsLocalDataSourceImpl(articleDAO)
    }
}