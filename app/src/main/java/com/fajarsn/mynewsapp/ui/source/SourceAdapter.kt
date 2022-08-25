package com.fajarsn.mynewsapp.ui.source

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fajarsn.mynewsapp.data.entity.SourceItem
import com.fajarsn.mynewsapp.databinding.ItemSourceBinding
import com.fajarsn.mynewsapp.ui.helper.BaseAdapter

class SourceAdapter(sources: List<SourceItem>) :
    BaseAdapter<SourceAdapter.ListViewHolder, SourceItem>(sources) {
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val source = list[position]
        val binding = holder.binding
        binding.sourceNameTextView.text = source.name
        binding.sourceDescriptionTextView.text = source.description
        binding.sourceUrlTextView.text = source.url
        val languageString = "${source.language} - ${source.country}"
        binding.sourceLanguageTextView.text = languageString

        binding.sourceCardView.setOnClickListener {
            onItemClickCallBack.onItemClicked(list[holder.bindingAdapterPosition])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemSourceBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ListViewHolder(binding)
    }

    inner class ListViewHolder(val binding: ItemSourceBinding) :
        RecyclerView.ViewHolder(binding.root)
}