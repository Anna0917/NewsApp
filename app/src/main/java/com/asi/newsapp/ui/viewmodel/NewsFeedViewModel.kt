package com.asi.newsapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.asi.newsapp.model.NewsArticle
import com.asi.newsapp.repository.NewsRepository
import com.asi.newsapp.storage.LocalStorage

class NewsFeedViewModel(private val newsRepository: NewsRepository, private val localStorage: LocalStorage) : ViewModel() {

    val newsLiveData: LiveData<List<NewsArticle>>

    private val mutableSearchTerm = MutableLiveData<String>()
    init{
        mutableSearchTerm.value = localStorage.getQuery()
        newsLiveData = Transformations.switchMap(mutableSearchTerm) { searchTerm ->
            newsRepository.searchNews(searchTerm)
        }
    }

    fun saveLastSearchQuery(query: String = "android"){
        localStorage.saveQuery(query)
        mutableSearchTerm.value = query
    }

}