package com.example.praktikum_news_hilt.presentation.di

import com.example.praktikum_news_hilt.data.repository.NewsRepositoryImpl
import com.example.praktikum_news_hilt.data.repository.datasource.NewsLocalDataSource
import com.example.praktikum_news_hilt.data.repository.datasource.NewsRemoteDataSource
import com.example.praktikum_news_hilt.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideNewsRepository(
        newsRemoteDataSource: NewsRemoteDataSource,
        newsLocalDataSource: NewsLocalDataSource
    ): NewsRepository {
        return NewsRepositoryImpl(newsRemoteDataSource, newsLocalDataSource)
    }
}