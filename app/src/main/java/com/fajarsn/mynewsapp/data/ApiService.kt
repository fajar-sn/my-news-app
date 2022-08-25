package com.fajarsn.mynewsapp.data

import com.fajarsn.mynewsapp.data.entity.ArticleResponse
import com.fajarsn.mynewsapp.data.entity.SourceResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines/sources")
    suspend fun getSources(@Query("category") category: String) : SourceResponse

    @GET("everything")
    suspend fun getArticles(
        @Query("sources") sources: String,
        @Query("page") page: Int,
        @Query("q") query: String = "",
        @Query("pageSize") pageSize: Int = 20,
        @Query("searchIn") searchIn: String = "title",
    ) : ArticleResponse
}