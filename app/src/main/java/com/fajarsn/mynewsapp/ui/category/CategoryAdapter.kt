package com.fajarsn.mynewsapp.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fajarsn.mynewsapp.data.entity.NewsCategory
import com.fajarsn.mynewsapp.databinding.ItemCategoryBinding
import com.fajarsn.mynewsapp.ui.BaseAdapter

class CategoryAdapter(categories: List<NewsCategory>) :
    BaseAdapter<CategoryAdapter.ListViewHolder, NewsCategory>(categories) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val category = list[position]

        holder.binding.apply {
            categoryNameTextView.text = category.name
            categoryPictureImageView.setImageResource(category.photo)

            categoryCardView.setOnClickListener {
                onItemClickCallBack.onItemClicked(list[holder.bindingAdapterPosition])
            }
        }
    }

    inner class ListViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)
}