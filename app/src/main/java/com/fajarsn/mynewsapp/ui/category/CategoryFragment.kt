package com.fajarsn.mynewsapp.ui.category

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.fajarsn.mynewsapp.R
import com.fajarsn.mynewsapp.data.entity.NewsCategory
import com.fajarsn.mynewsapp.databinding.FragmentCategoryBinding
import com.fajarsn.mynewsapp.ui.BaseAdapter
import com.fajarsn.mynewsapp.ui.BaseFragment

class CategoryFragment : BaseFragment() {
    private lateinit var categoryAdapter: CategoryAdapter

    private val categories: ArrayList<NewsCategory>
        @SuppressLint("Recycle")
        get() {
            val names = resources.getStringArray(R.array.category_name)
            val photos = resources.obtainTypedArray(R.array.category_photo)
            val categories = ArrayList<NewsCategory>()

            for (i in names.indices) {
                val category = NewsCategory(names[i], photos.getResourceId(i, -1))
                categories.add(category)
            }

            return categories
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupView() {
        val recyclerView = (binding as FragmentCategoryBinding).categoryRecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        categoryAdapter = CategoryAdapter(categories)
        recyclerView.adapter = categoryAdapter
    }

    override fun setupAction() =
        categoryAdapter.setOnItemClickCallback(object : BaseAdapter.OnItemClickCallBack {
            override fun <NewsCategory> onItemClicked(data: NewsCategory) {
                Log.e("CLICKED ", "$data")
            }
        })

    override fun setupViewModel() {}
}