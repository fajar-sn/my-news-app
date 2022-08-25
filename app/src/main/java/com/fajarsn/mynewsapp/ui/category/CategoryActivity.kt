package com.fajarsn.mynewsapp.ui.category

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.fajarsn.mynewsapp.R
import com.fajarsn.mynewsapp.data.entity.NewsCategory
import com.fajarsn.mynewsapp.databinding.LayoutRecyclerViewBinding
import com.fajarsn.mynewsapp.ui.helper.BaseAdapter
import com.fajarsn.mynewsapp.ui.helper.RecyclerViewActivity
import com.fajarsn.mynewsapp.ui.source.SourceActivity

class CategoryActivity : RecyclerViewActivity<CategoryAdapter.ListViewHolder, NewsCategory>() {
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
        val recyclerView = (binding as LayoutRecyclerViewBinding).recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter = CategoryAdapter(categories)
        recyclerView.adapter = adapter
    }

    override fun setupAction() {
        adapter.setOnItemClickCallback(object : BaseAdapter.OnItemClickCallBack<NewsCategory> {
            override fun onItemClicked(data: NewsCategory) {
                val intent = Intent(this@CategoryActivity, SourceActivity::class.java)
                intent.putExtra(SourceActivity.EXTRA_CATEGORY, data)
                startActivity(intent)
            }
        })
    }

    override fun setupViewModel() {}
}