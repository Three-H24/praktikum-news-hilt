package com.example.praktikum_news_hilt.presentation.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.praktikum_news_hilt.data.model.APIResponse
import com.example.praktikum_news_hilt.data.model.Article
import com.example.praktikum_news_hilt.data.util.Resource
import com.example.praktikum_news_hilt.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel @ViewModelInject constructor(
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
    private val getSearchNewsUseCase: GetSearchNewsUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val getSavedNewsUseCase: GetSavedNewsUseCase,
    private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase,
) : ViewModel() {

    val newsHeadlines: MutableLiveData<Resource<APIResponse>> = MutableLiveData()
    fun getNewsHeadlines(country: String, page: Int) = viewModelScope.launch(Dispatchers.IO) {
        newsHeadlines.postValue(Resource.Loading())
        try {
            val apiResult = getNewsHeadlinesUseCase.execute(country, page)
            newsHeadlines.postValue(apiResult)
        } catch (e: Exception) {
            newsHeadlines.postValue(Resource.Error(e.message))
        }
    }

    // Search news
    val searchNews: MutableLiveData<Resource<APIResponse>> = MutableLiveData()
    fun getSearchNews(country: String, page: Int, querySearch: String) =
        viewModelScope.launch(Dispatchers.IO) {
            searchNews.postValue(Resource.Loading())
            try {
                val apiResult = getSearchNewsUseCase.execute(country, page, querySearch)
                searchNews.postValue(apiResult)
            } catch (e: Exception) {
                searchNews.postValue(Resource.Error(e.message))
            }
        }

    // Save article to Local data
    fun saveArticle(article: Article) = viewModelScope.launch {
        saveNewsUseCase.execute(article)
    }

    // Get saved news
    fun getSavedNews() = liveData {
        getSavedNewsUseCase.execute().collect {
            emit(it)
        }
    }

    // Delete article
    fun deleteArticle(article: Article) = viewModelScope.launch {
        deleteSavedNewsUseCase.execute(article)
    }
}