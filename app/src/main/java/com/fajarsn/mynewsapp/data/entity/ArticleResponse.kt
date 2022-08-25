package com.fajarsn.mynewsapp.data.entity

import android.os.Parcelable
import com.fajarsn.mynewsapp.data.BaseResponse
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ArticleResponse(

	@field:SerializedName("articles")
	val articles: List<Article>
) : BaseResponse()

@Parcelize
data class Article(

	@field:SerializedName("publishedAt")
	val publishedAt: String,

	@field:SerializedName("author")
	val author: String,

	@field:SerializedName("urlToImage")
	val urlToImage: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("content")
	val content: String
) : Parcelable
