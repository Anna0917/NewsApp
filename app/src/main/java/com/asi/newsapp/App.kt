package com.asi.newsapp

import android.app.Application
import com.asi.newsapp.di.AppInjector

class App : Application(){
    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
    }
}