package com.asi.newsapp.di

import com.asi.newsapp.api.NewsService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val newsServiceModule = module {
    single {
        retrofit()
    }
    single{
        get<Retrofit>().create(NewsService::class.java)
    }
}

private fun retrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}