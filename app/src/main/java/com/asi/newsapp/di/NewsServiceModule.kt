package com.asi.newsapp.di

import com.asi.newsapp.api.NewsService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.asi.newsapp.api.NewsInterceptor

val newsServiceModule = module {
    single {
        retrofit()
    }
    single{
        get<Retrofit>().create(NewsService::class.java)
    }
}

private fun retrofit(): Retrofit {
    val client = OkHttpClient.Builder()
        .addInterceptor(NewsInterceptor())
        .build()

    return Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
}