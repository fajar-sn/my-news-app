package com.fajarsn.mynewsapp.data.entity

import com.fajarsn.mynewsapp.data.BaseResponse
import com.google.gson.annotations.SerializedName

data class SourceListResponse(

	@field:SerializedName("sources")
	val sources: List<SourceResponse>
) : BaseResponse()

data class SourceResponse(

	@field:SerializedName("country")
	val country: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("language")
	val language: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("url")
	val url: String
)
