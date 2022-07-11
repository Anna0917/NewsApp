package com.asi.newsapp.util

class UtilDate {
    companion object{
        //         formatDate = "yyyy-MM-dd"
        fun getSimpleFormatDate(dateString: String) = dateString.substringBefore('T')
    }
}