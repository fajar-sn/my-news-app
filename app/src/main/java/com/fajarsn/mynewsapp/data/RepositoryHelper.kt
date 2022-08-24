package com.fajarsn.mynewsapp.data

sealed class Result private constructor() {
    data class Success<out T : BaseResponse>(val data: T) : Result()
    data class Error(val error: String) : Result()
    object Loading : Result()
}

open class BaseResponse