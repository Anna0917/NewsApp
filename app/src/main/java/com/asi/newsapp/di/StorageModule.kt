package com.asi.newsapp.di


import com.asi.newsapp.storage.LocalStorage
import com.asi.newsapp.storage.SharedPrefQueryStorage
import org.koin.dsl.module

val storageModule = module {
    single<LocalStorage> {
        SharedPrefQueryStorage(get())
    }
}