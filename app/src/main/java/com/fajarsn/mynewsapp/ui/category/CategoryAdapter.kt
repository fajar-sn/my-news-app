package com.fajarsn.mynewsapp.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fajarsn.mynewsapp.data.entity.NewsCategory
import com.fajarsn.mynewsapp.databinding.ItemCategoryBinding
import com.fajarsn.mynewsapp.ui.helper.BaseAdapter

class CategoryAdapter(categories: List<NewsCategory>) :
    BaseAdapter<CategoryAdapter.ListViewHolder, NewsCategory>(categories) {
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val category = list[position]
        val binding = holder.binding
        binding.categoryNameTextView.text = category.name
        binding.categoryPictureImageView.setImageResource(category.photo)
        binding.categoryCardView.setOnClickListener {
            onItemClickCallBack.onItemClicked(list[holder.bindingAdapterPosition])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ListViewHolder(binding)
    }

    inner class ListViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)
}