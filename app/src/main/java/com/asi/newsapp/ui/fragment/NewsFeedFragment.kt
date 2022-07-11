package com.asi.newsapp.ui.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asi.newsapp.R
import com.asi.newsapp.model.NewsArticle
import com.asi.newsapp.ui.viewmodel.NewsFeedViewModel
import com.asi.newsapp.util.UtilDate
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsFeedFragment : Fragment() {

    private var callbacks: Callbacks? = null

    private val newsFeedViewModel by viewModel<NewsFeedViewModel>()

    private lateinit var newsFeedRecyclerView: RecyclerView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news_feed, container, false)
        newsFeedRecyclerView = view.findViewById(R.id.news_recycler_view)
        newsFeedRecyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsFeedViewModel.newsLiveData.observe(
            viewLifecycleOwner,
            Observer {
                newsFeedRecyclerView.adapter = NewsAdapter(it)
            }
        )
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    companion object {
        fun newInstance() = NewsFeedFragment()
    }


    private inner class NewsAdapter(private val newsArticles: List<NewsArticle>) :
        RecyclerView.Adapter<NewsAdapter.NewsArticleViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsArticleViewHolder =
            NewsArticleViewHolder(layoutInflater.inflate(R.layout.item_news_article, parent, false))


        override fun getItemCount(): Int = newsArticles.size

        override fun onBindViewHolder(holder: NewsArticleViewHolder, position: Int) =
            holder.bind(newsArticles[position])


        inner class NewsArticleViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
            private lateinit var newsArticle: NewsArticle

            val titleTextView: TextView = itemView.findViewById(R.id.news_title)
            val authorTextView: TextView = itemView.findViewById(R.id.news_author)
            val dateTextView: TextView = itemView.findViewById(R.id.news_date)
            val newsImageView: ImageView = itemView.findViewById(R.id.news_image)

            init {
                itemView.setOnClickListener(this)
            }

            fun bind(newsArticle: NewsArticle) {
                this.newsArticle = newsArticle

                titleTextView.text = newsArticle.title
                authorTextView.text = newsArticle.author
                dateTextView.text = UtilDate.getSimpleFormatDate(newsArticle.publishedAt)

                Glide.with(newsImageView.context)
                    .load(newsArticle.urlToImage)
                    .apply(
                        RequestOptions()
                        .placeholder(R.drawable.images)
                        .error(R.drawable.images)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .centerCrop()
                    .into(newsImageView)

            }

            override fun onClick(v: View?) {
                callbacks?.onNewsArticlesSelected(newsArticle.url.toUri())
            }
        }
    }

    interface Callbacks {
        fun onNewsArticlesSelected(uri: Uri)
    }
}