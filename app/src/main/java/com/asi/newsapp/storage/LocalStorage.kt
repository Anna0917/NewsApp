package com.asi.newsapp.storage

interface LocalStorage {
    fun saveQuery(saveQuery: String): Boolean
    fun getQuery(): String
}