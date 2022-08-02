package com.asi.newsapp.api

import android.util.Log
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

private const val API_KEY = "4b89e4fa1f1a4e9dbeea14a8087374c4"
class NewsInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest: Request = chain.request()
        val newUrl: HttpUrl = originRequest.url().newBuilder()
            .addQueryParameter("sortBy", "popularity")
            .addQueryParameter("apiKey", API_KEY)
            .build()
        val newRequest: Request = originRequest.newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }
}