package com.fajarsn.mynewsapp.ui.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fajarsn.mynewsapp.data.entity.Article
import com.fajarsn.mynewsapp.databinding.ActivityArticleWebViewBinding

class ArticleWebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityArticleWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val article = intent.getParcelableExtra<Article>(EXTRA_ARTICLE) as Article
        supportActionBar?.title = article.title
        binding.webView.loadUrl(article.url)
    }

    companion object {
        const val EXTRA_ARTICLE = "extra_article"
    }
}