package com.fajarsn.mynewsapp.ui.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fajarsn.mynewsapp.databinding.ItemLoadingBinding

class ArticleLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<ArticleLoadStateAdapter.LoadStateViewHolder>() {
    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) = holder.bind(loadState)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding, retry)
    }

    class LoadStateViewHolder(private val binding: ItemLoadingBinding, retry: () -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.retryButton2.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error)
                binding.errorTextView2.text = loadState.error.localizedMessage

            binding.progressBar2.isVisible = loadState is LoadState.Loading
            binding.retryButton2.isVisible = loadState is LoadState.Error
            binding.errorTextView2.isVisible = loadState is LoadState.Error
        }
    }
}