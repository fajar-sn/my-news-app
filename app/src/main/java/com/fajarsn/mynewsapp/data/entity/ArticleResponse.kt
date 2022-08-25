package com.fajarsn.mynewsapp.data.entity

import com.fajarsn.mynewsapp.data.BaseResponse
import com.google.gson.annotations.SerializedName

data class ArticleResponse(

	@field:SerializedName("articles")
	val articles: List<Article>
) : BaseResponse()

data class Article(

	@field:SerializedName("publishedAt")
	val publishedAt: String,

	@field:SerializedName("author")
	val author: String,

	@field:SerializedName("urlToImage")
	val urlToImage: String,

	@field:SerializedName("description")
	val description: String,

//	@field:SerializedName("source")
//	val source: Source,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("content")
	val content: String
)

//data class Source(
//
//	@field:SerializedName("name")
//	val name: String,
//
//	@field:SerializedName("id")
//	val id: String
//)
