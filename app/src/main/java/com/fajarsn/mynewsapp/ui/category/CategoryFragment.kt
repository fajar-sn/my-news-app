package com.fajarsn.mynewsapp.ui.category

import android.annotation.SuppressLint
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.fajarsn.mynewsapp.R
import com.fajarsn.mynewsapp.data.entity.NewsCategory
import com.fajarsn.mynewsapp.databinding.FragmentRecyclerViewBinding
import com.fajarsn.mynewsapp.ui.helper.BaseAdapter
import com.fajarsn.mynewsapp.ui.helper.RecyclerViewFragment

class CategoryFragment : RecyclerViewFragment<CategoryAdapter.ListViewHolder, NewsCategory>() {
    private val categories: ArrayList<NewsCategory>
        @SuppressLint("Recycle")
        get() {
            val names = resources.getStringArray(R.array.category_name)
            val values = resources.getStringArray(R.array.category_value)
            val photos = resources.obtainTypedArray(R.array.category_photo)
            val categories = ArrayList<NewsCategory>()

            for (i in names.indices) {
                val category = NewsCategory(names[i], values[i], photos.getResourceId(i, -1))
                categories.add(category)
            }

            return categories
        }

    override fun setupView() {
        val recyclerView = (binding as FragmentRecyclerViewBinding).gridRecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = CategoryAdapter(categories)
        recyclerView.adapter = adapter
    }

    override fun setupAction() =
        adapter.setOnItemClickCallback(object : BaseAdapter.OnItemClickCallBack<NewsCategory> {
            override fun onItemClicked(data: NewsCategory) {
                val toSourceFragment =
                    CategoryFragmentDirections.actionCategoryFragmentToSourceFragment(data)
                view?.findNavController()?.navigate(toSourceFragment)
            }
        })

    override fun setupViewModel() {}
}