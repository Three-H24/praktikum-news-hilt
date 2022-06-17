package com.example.praktikum_news_hilt.presentation.di

import com.example.praktikum_news_hilt.data.api.NewsAPIService
import com.example.praktikum_news_hilt.data.repository.datasource.NewsRemoteDataSource
import com.example.praktikum_news_hilt.data.repository.datasourceImpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideRemoteDataSource(newsAPIService: NewsAPIService): NewsRemoteDataSource {
        return NewsRemoteDataSourceImpl(newsAPIService)
    }
}