package com.asi.newsapp.di

import com.asi.newsapp.App
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

object AppInjector {
    fun init(app: App){
        startKoin {
            androidContext(app)
            modules(listOf(
                newsServiceModule,
                repositoryModule,
                storageModule,
                viewModelModule
            ))
        }
    }
}