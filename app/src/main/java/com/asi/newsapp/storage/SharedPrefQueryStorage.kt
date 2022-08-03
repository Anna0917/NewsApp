package com.asi.newsapp.storage

import android.content.Context
import com.asi.newsapp.storage.LocalStorage

private const val SHARED_PREF_NAME = "shared_pref_name"
private const val PREF_SEARCH_QUERY = "searchQuery"
private const val DEFAULT_VALUE = "android"


class SharedPrefQueryStorage(context: Context) : LocalStorage {
    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    override fun saveQuery(saveQuery: String): Boolean {
        sharedPreferences.edit().putString(PREF_SEARCH_QUERY, saveQuery).apply()
        return true
    }

    override fun getQuery(): String {
        return sharedPreferences.getString(PREF_SEARCH_QUERY, DEFAULT_VALUE) ?: DEFAULT_VALUE
    }

}