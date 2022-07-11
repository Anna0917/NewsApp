package com.asi.newsapp.model


data class NewsArticle(
    var author: String = "",
    var title: String = "",
    var description:String = "",
    var url: String = "",
    var urlToImage: String = "",
    var publishedAt: String = ""
)