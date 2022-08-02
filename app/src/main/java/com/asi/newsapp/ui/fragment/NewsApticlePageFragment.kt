package com.asi.newsapp.ui.fragment

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.asi.newsapp.R
import com.asi.newsapp.databinding.FragmentNewsArticlePageBinding

private const val ARG_URI = "news_article_page_url"

class NewsArticlePageFragment : Fragment() {

    private var _binding: FragmentNewsArticlePageBinding? = null
    private val binding: FragmentNewsArticlePageBinding get() = _binding!!

    private lateinit var uri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uri = arguments?.getParcelable(ARG_URI) ?: Uri.EMPTY
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsArticlePageBinding.inflate(inflater, container, false)
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl(uri.toString())
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun goBack():Boolean{
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
            return true
        }
        return false
    }

    companion object{
        const val TAG = "PhotoPageFragment"
        fun newInstance(uri: Uri) : NewsArticlePageFragment{
            return NewsArticlePageFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_URI, uri)
                }
            }
        }
    }

}