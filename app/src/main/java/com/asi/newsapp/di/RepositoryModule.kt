package com.asi.newsapp.di

import com.asi.newsapp.repository.NewsRepository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        NewsRepository(get())
    }
}