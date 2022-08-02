package com.asi.newsapp.api


import com.asi.newsapp.model.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("everything?q=android")
    fun fetchNews(): Call<NewsResponse>

    @GET("everything")
    fun searchNews(@Query("q") query: String): Call<NewsResponse>
}