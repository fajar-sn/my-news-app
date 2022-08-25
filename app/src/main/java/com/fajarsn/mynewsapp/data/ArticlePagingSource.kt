package com.fajarsn.mynewsapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fajarsn.mynewsapp.data.entity.Article

class ArticlePagingSource(
    private val service: ApiService,
    private val sources: String,
    private val query: String = ""
) : PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>) = state.anchorPosition?.let {
        val anchorPage = state.closestPageToPosition(it)
        anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>) = try {
        val position = params.key ?: INITIAL_PAGE_INDEX
        val response = service.getArticles(sources, position, query, params.loadSize)

        LoadResult.Page(
            data = response.articles,
            prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
            nextKey = if (response.articles.size != params.loadSize) null else position + 1
        )
    } catch (exception : Exception) {
        LoadResult.Error(exception)
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}