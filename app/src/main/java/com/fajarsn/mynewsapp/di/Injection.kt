package com.fajarsn.mynewsapp.di

import android.content.Context
import com.fajarsn.mynewsapp.data.ApiConfig
import com.fajarsn.mynewsapp.data.Repository

object Injection {
    fun provideRepository(context: Context) : Repository {
        val service = ApiConfig.getApiService(context)
        return Repository.getInstance(service)
    }
}