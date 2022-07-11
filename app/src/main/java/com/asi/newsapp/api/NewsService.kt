package com.asi.newsapp.api


import com.asi.newsapp.model.NewsResponse
import retrofit2.Call
import retrofit2.http.GET

private const val API_KEY = "4b89e4fa1f1a4e9dbeea14a8087374c4"
interface NewsService {
    @GET("everything?q=android&apiKey=$API_KEY")
    fun fetchNews(): Call<NewsResponse>
}