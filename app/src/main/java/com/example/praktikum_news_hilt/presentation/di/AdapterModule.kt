package com.example.praktikum_news_hilt.presentation.di

import com.example.praktikum_news_hilt.presentation.adapter.NewsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AdapterModule {

    @Singleton
    @Provides
    fun provideNewsAdapter() : NewsAdapter {
        return NewsAdapter()
    }
}