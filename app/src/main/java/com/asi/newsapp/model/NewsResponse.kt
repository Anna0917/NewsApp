package com.asi.newsapp.model

import com.google.gson.annotations.SerializedName

class NewsResponse {
    @SerializedName("articles")
    lateinit var newsArticles: List<NewsArticle>
}