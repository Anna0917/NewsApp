package com.asi.newsapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.asi.newsapp.api.NewsService
import com.asi.newsapp.model.NewsArticle
import com.asi.newsapp.model.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository (private val newsService: NewsService){

    fun getNews(): LiveData<List<NewsArticle>> {
        val responseLiveData: MutableLiveData<List<NewsArticle>> = MutableLiveData()

        val newsRequest: Call<NewsResponse> = newsService.fetchNews()
        newsRequest.enqueue(object : Callback<NewsResponse> {
            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                val newsResponse: NewsResponse? = response.body()
                var newsArticles: List<NewsArticle> = newsResponse?.newsArticles ?: mutableListOf()
                newsArticles = newsArticles.filterNot{it.url.isBlank()}
                responseLiveData.value = newsArticles
            }
        })
        return responseLiveData
    }
}