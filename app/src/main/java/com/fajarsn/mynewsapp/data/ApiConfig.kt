package com.fajarsn.mynewsapp.data

import android.content.Context
import android.content.pm.PackageManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.fajarsn.mynewsapp.BuildConfig
import okhttp3.Interceptor

object ApiConfig {
    private const val baseUrl = "https://api.github.com/"
    private lateinit var apiKey: String

    fun getApiService(context: Context): ApiService {
        val loggingInterceptor =
            if (BuildConfig.DEBUG)
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            else
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)

        context.packageManager
            .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            .apply {
                apiKey = metaData.getString("API_KEY").toString()
            }

        val client = OkHttpClient.Builder().apply {
            addInterceptor(
                Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                    builder.header("X-Api-Key", apiKey)
                    return@Interceptor chain.proceed(builder.build())
                }
            )
            addInterceptor(loggingInterceptor)
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client).build()

        return retrofit.create(ApiService::class.java)
    }
}