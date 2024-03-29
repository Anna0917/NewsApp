package com.asi.newsapp.ui.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asi.newsapp.R
import com.asi.newsapp.databinding.ActivityNewsFeedBinding
import com.asi.newsapp.databinding.FragmentNewsFeedBinding
import com.asi.newsapp.databinding.ItemNewsArticleBinding
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

    private var _binding: FragmentNewsFeedBinding? = null
    private val binding: FragmentNewsFeedBinding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsFeedBinding.inflate(inflater, container, false)
        binding.newsRecyclerView.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsFeedViewModel.newsLiveData.observe(
            viewLifecycleOwner,
            Observer {
                binding.newsRecyclerView.adapter = NewsAdapter(it)
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_news_feed, menu)
        val searchItem: MenuItem = menu.findItem(R.id.menu_item_search)
        val searchView = searchItem.actionView as SearchView
        searchView.apply{
            setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String): Boolean {
                    newsFeedViewModel.saveLastSearchQuery(query)
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_item_clear -> {
                newsFeedViewModel.saveLastSearchQuery()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        fun newInstance() = NewsFeedFragment()
    }


    private inner class NewsAdapter(private val newsArticles: List<NewsArticle>) :
        RecyclerView.Adapter<NewsAdapter.NewsArticleViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsArticleViewHolder {
            val b = ItemNewsArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return NewsArticleViewHolder(b)
        }

        override fun getItemCount(): Int = newsArticles.size

        override fun onBindViewHolder(holder: NewsArticleViewHolder, position: Int) =
            holder.bind(newsArticles[position])



        inner class NewsArticleViewHolder(val holderBinding: ItemNewsArticleBinding) : RecyclerView.ViewHolder(holderBinding.root), View.OnClickListener {
            private lateinit var newsArticle: NewsArticle

            init {
                itemView.setOnClickListener(this)
            }

            fun bind(newsArticle: NewsArticle) {
                this.newsArticle = newsArticle

                holderBinding.newsTitle.text = newsArticle.title
                holderBinding.newsAuthor.text = newsArticle.author
                holderBinding.newsDate.text = UtilDate.getSimpleFormatDate(newsArticle.publishedAt)

                Glide.with(holderBinding.newsImage.context)
                    .load(newsArticle.urlToImage)
                    .apply(
                        RequestOptions()
                        .placeholder(R.drawable.images)
                        .error(R.drawable.images)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .centerCrop()
                    .into(holderBinding.newsImage)

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