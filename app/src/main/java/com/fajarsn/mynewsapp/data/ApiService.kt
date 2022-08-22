package com.fajarsn.mynewsapp.data

import com.fajarsn.mynewsapp.data.entity.SourceListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("sources")
    suspend fun getSources(@Query("category") category: String) : SourceListResponse
}