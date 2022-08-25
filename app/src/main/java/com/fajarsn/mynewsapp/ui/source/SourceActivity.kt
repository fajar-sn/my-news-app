package com.fajarsn.mynewsapp.ui.source

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fajarsn.mynewsapp.data.Result
import com.fajarsn.mynewsapp.data.entity.NewsCategory
import com.fajarsn.mynewsapp.data.entity.SourceItem
import com.fajarsn.mynewsapp.data.entity.SourceResponse
import com.fajarsn.mynewsapp.databinding.LayoutRecyclerViewBinding
import com.fajarsn.mynewsapp.ui.helper.BaseAdapter
import com.fajarsn.mynewsapp.ui.helper.RecyclerViewActivity
import com.fajarsn.mynewsapp.ui.helper.SourceViewModel
import com.fajarsn.mynewsapp.ui.helper.ViewModelFactory

class SourceActivity : RecyclerViewActivity<SourceAdapter.ListViewHolder, SourceItem>() {
    private lateinit var category: NewsCategory
    private lateinit var recyclerView: RecyclerView

    private val errorCallback: () -> Unit =
        { (viewModel as SourceViewModel).getSources(category.value) }

    private val successCallback: (Result.Success<SourceResponse>) -> Unit = { result ->
        adapter = SourceAdapter(result.data.sources)

        adapter.setOnItemClickCallback(object : BaseAdapter.OnItemClickCallBack<SourceItem> {
            override fun onItemClicked(data: SourceItem) {
                Log.e("SourceFragment", "$data")
            }
        })

        recyclerView.adapter = adapter
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
    }

    override fun setupViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        val viewModel: SourceViewModel by viewModels { factory }
        this.viewModel = viewModel
        viewModel.getSources(category.value)
        viewModel.result.observe(this) { observeResultLiveData(it, errorCallback, successCallback) }
    }

    override fun setupAction() {}

    companion object {
        const val EXTRA_CATEGORY = "extra_category"
    }
}