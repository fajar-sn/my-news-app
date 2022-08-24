package com.fajarsn.mynewsapp.ui.source

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fajarsn.mynewsapp.data.Result
import com.fajarsn.mynewsapp.data.entity.NewsCategory
import com.fajarsn.mynewsapp.data.entity.SourceListResponse
import com.fajarsn.mynewsapp.data.entity.SourceResponse
import com.fajarsn.mynewsapp.databinding.FragmentRecyclerViewBinding
import com.fajarsn.mynewsapp.ui.helper.*

class SourceFragment : RecyclerViewFragment<SourceAdapter.ListViewHolder, SourceResponse>() {
    private lateinit var category: NewsCategory
    private lateinit var recyclerView: RecyclerView

    private val errorCallback: () -> Unit =
        { (viewModel as SourceViewModel).getSources(category.value) }

    private val successCallback: (Result.Success<SourceListResponse>) -> Unit = { result ->
        adapter = SourceAdapter(result.data.sources)

        adapter.setOnItemClickCallback(object : BaseAdapter.OnItemClickCallBack<SourceResponse> {
            override fun onItemClicked(data: SourceResponse) {
                Log.e("SourceFragment", "$data")
            }
        })

        recyclerView.adapter = adapter
    }

    override fun setupView() {
        val binding = binding as FragmentRecyclerViewBinding
        category = SourceFragmentArgs.fromBundle(arguments as Bundle).category
        (activity as MainActivity).setActionBarTitle(category.name)
        errorLayout = binding.gridErrorLayout
        progressBar = binding.gridProgressBar
        recyclerView = binding.gridRecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun setupViewModel() {
        val factory = ViewModelFactory.getInstance(requireContext())
        val viewModel: SourceViewModel by viewModels { factory }
        this.viewModel = viewModel
        viewModel.getSources(category.value)
        viewModel.result.observe(requireActivity()) {
            observeResultLiveData(it,
                errorCallback,
                successCallback)
        }
    }

    override fun setupAction() {}
}