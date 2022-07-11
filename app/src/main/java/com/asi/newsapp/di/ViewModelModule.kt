package com.asi.newsapp.di

import com.asi.newsapp.ui.viewmodel.NewsFeedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{
        NewsFeedViewModel(get())
    }
}