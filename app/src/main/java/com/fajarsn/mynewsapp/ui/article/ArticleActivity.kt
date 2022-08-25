package com.fajarsn.mynewsapp.ui.article

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.fajarsn.mynewsapp.R
import com.fajarsn.mynewsapp.data.entity.Article
import com.fajarsn.mynewsapp.data.entity.SourceItem
import com.fajarsn.mynewsapp.databinding.LayoutRecyclerViewBinding
import com.fajarsn.mynewsapp.ui.helper.ArticleViewModel
import com.fajarsn.mynewsapp.ui.helper.BaseActivity
import com.fajarsn.mynewsapp.ui.helper.BaseAdapter
import com.fajarsn.mynewsapp.ui.helper.ViewModelFactory
import com.fajarsn.mynewsapp.ui.webview.ArticleWebViewActivity

class ArticleActivity : BaseActivity() {
    private lateinit var source: SourceItem
    private lateinit var adapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = LayoutRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initActivity()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.article_search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                (viewModel as ArticleViewModel).getArticles(source.id, query ?: "")
                    .observe(this@ArticleActivity) {
                        adapter.submitData(lifecycle, it)
                    }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (viewModel as ArticleViewModel).getArticles(source.id, newText ?: "")
                    .observe(this@ArticleActivity) {
                        adapter.submitData(lifecycle, it)
                    }
                return true
            }
        })

        return true
    }

    override fun setupViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        val viewModel: ArticleViewModel by viewModels { factory }
        this.viewModel = viewModel
        source = intent.getParcelableExtra<SourceItem>(EXTRA_SOURCES) as SourceItem
    }

    override fun setupView() {
        supportActionBar?.title = source.name
        val binding = binding as LayoutRecyclerViewBinding
        val recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ArticleAdapter()

        adapter.setOnItemClickCallback(object : BaseAdapter.OnItemClickCallBack<Article> {
            override fun onItemClicked(data: Article) {
                val intent = Intent(this@ArticleActivity, ArticleWebViewActivity::class.java)
                intent.putExtra(ArticleWebViewActivity.EXTRA_ARTICLE, data)
                startActivity(intent)
            }
        })

        recyclerView.adapter = adapter.withLoadStateFooter(
            footer = ArticleLoadStateAdapter { adapter.retry() }
        )

        (viewModel as ArticleViewModel).getArticles(source.id).observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }

    override fun setupAction() {}

    companion object {
        const val EXTRA_SOURCES = "extra_sources"
    }
}