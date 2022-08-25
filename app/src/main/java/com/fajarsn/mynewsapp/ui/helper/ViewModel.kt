package com.fajarsn.mynewsapp.ui.helper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.fajarsn.mynewsapp.data.Repository
import com.fajarsn.mynewsapp.data.Result
import kotlinx.coroutines.launch

open class BaseViewModel(protected open val repository: Repository) : ViewModel() {
    protected val mutableResult = MutableLiveData<Result>()
    val result: LiveData<Result> = mutableResult
}

class SourceViewModel(repository: Repository) : BaseViewModel(repository) {
    fun getSources(category: String) = viewModelScope.launch {
        mutableResult.value = Result.Loading
        repository.getSources(category, mutableResult)
    }
}

class ArticleViewModel(repository: Repository) : BaseViewModel(repository) {
    fun getArticles(sources: String, query: String = "") =
        repository.getArticles(sources, query).cachedIn(viewModelScope)
}