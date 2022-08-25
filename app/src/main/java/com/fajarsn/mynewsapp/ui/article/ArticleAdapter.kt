package com.fajarsn.mynewsapp.ui.article

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fajarsn.mynewsapp.data.entity.Article
import com.fajarsn.mynewsapp.databinding.ItemArticleBinding
import com.fajarsn.mynewsapp.ui.helper.BaseAdapter

class ArticleAdapter : PagingDataAdapter<Article, ArticleAdapter.ArticleViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallBack: BaseAdapter.OnItemClickCallBack<Article>

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        if (article != null) holder.bind(article)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemArticleBinding.inflate(layoutInflater, parent, false)
        return ArticleViewHolder(binding)
    }

    fun setOnItemClickCallback(onItemClickCallBack: BaseAdapter.OnItemClickCallBack<Article>) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    inner class ArticleViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.articleAuthorTextView.text = article.author
            binding.articleTitleTextView.text = article.title
            binding.articlePublishedTimeTextView.text = article.publishedAt
            Glide.with(itemView.context).load(article.urlToImage).into(binding.articleImageView)

            itemView.setOnClickListener { onItemClickCallBack.onItemClicked(article) }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article) =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Article, newItem: Article) =
                oldItem.content == newItem.content
        }
    }
}