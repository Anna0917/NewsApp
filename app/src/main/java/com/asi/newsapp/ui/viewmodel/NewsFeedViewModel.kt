package com.asi.newsapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.asi.newsapp.model.NewsArticle
import com.asi.newsapp.repository.NewsRepository

class NewsFeedViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    val newsLiveData: LiveData<List<NewsArticle>> = newsRepository.getNews()
}