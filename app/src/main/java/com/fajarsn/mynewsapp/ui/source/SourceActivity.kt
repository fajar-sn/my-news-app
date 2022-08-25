package com.fajarsn.mynewsapp.ui.source

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fajarsn.mynewsapp.R
import com.fajarsn.mynewsapp.data.Result
import com.fajarsn.mynewsapp.data.entity.NewsCategory
import com.fajarsn.mynewsapp.data.entity.SourceItem
import com.fajarsn.mynewsapp.data.entity.SourceResponse
import com.fajarsn.mynewsapp.databinding.LayoutRecyclerViewBinding
import com.fajarsn.mynewsapp.ui.article.ArticleActivity
import com.fajarsn.mynewsapp.ui.helper.BaseAdapter
import com.fajarsn.mynewsapp.ui.helper.RecyclerViewActivity
import com.fajarsn.mynewsapp.ui.helper.SourceViewModel
import com.fajarsn.mynewsapp.ui.helper.ViewModelFactory

class SourceActivity : RecyclerViewActivity<SourceAdapter.ListViewHolder, SourceItem>() {
    private lateinit var category: NewsCategory
    private lateinit var recyclerView: RecyclerView
    private var sources: List<SourceItem> = listOf()

    private val errorCallback: () -> Unit =
        { (viewModel as SourceViewModel).getSources(category.value) }

    private val successCallback: (Result.Success<SourceResponse>) -> Unit = { result ->
        sources = result.data.sources
        setRecyclerViewAdapter(sources)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.source_search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val filteredSources =
                    sources.filter { it.name.lowercase().contains(query?.lowercase() ?: "") }

                searchView.clearFocus()
                setRecyclerViewAdapter(filteredSources)
                return sources.isNotEmpty()
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == null || newText.isNotBlank()) setRecyclerViewAdapter(sources)
                return sources.isNotEmpty()
            }
        })

        return true
    }

    override fun setupView() {
        val binding = binding as LayoutRecyclerViewBinding
        category = intent.getParcelableExtra<NewsCategory>(EXTRA_CATEGORY) as NewsCategory
        supportActionBar?.title = category.name
        errorLayout = binding.errorLayout
        progressBar = binding.progressBar
        recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val viewModel = viewModel as SourceViewModel
        viewModel.getSources(category.value)
        viewModel.result.observe(this) { observeResultLiveData(it, errorCallback, successCallback) }
    }

    override fun setupViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        val viewModel: SourceViewModel by viewModels { factory }
        this.viewModel = viewModel
    }

    override fun setupAction() {}

    private fun setRecyclerViewAdapter(sources: List<SourceItem>) {
        adapter = SourceAdapter(sources)

        adapter.setOnItemClickCallback(object : BaseAdapter.OnItemClickCallBack<SourceItem> {
            override fun onItemClicked(data: SourceItem) {
                val intent = Intent(this@SourceActivity, ArticleActivity::class.java)
                intent.putExtra(ArticleActivity.EXTRA_SOURCES, data)
                startActivity(intent)
            }
        })

        recyclerView.adapter = adapter
    }

    companion object {
        const val EXTRA_CATEGORY = "extra_category"
    }
}