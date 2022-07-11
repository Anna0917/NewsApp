package com.asi.newsapp.ui.activity

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asi.newsapp.R
import com.asi.newsapp.ui.fragment.NewsArticlePageFragment
import com.asi.newsapp.ui.fragment.NewsFeedFragment

class NewsFeedActivity : AppCompatActivity() , NewsFeedFragment.Callbacks{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_feed)
        val isFragmentContainerEmpty = savedInstanceState == null
        if(isFragmentContainerEmpty){
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, NewsFeedFragment.newInstance())
                .commit()
        }
    }

    override fun onNewsArticlesSelected(uri: Uri) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, NewsArticlePageFragment.newInstance(uri), NewsArticlePageFragment.TAG)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        val webViewFragment = supportFragmentManager
            .findFragmentByTag(NewsArticlePageFragment.TAG) as NewsArticlePageFragment
        if(webViewFragment.goBack())
            return
        super.onBackPressed()
    }
}