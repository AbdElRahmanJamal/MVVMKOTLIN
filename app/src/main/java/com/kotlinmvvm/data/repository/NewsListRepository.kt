package com.kotlinmvvm.data.repository

import androidx.lifecycle.LiveData
import com.kotlinmvvm.data.ResultState
import com.kotlinmvvm.data.datastore.NewsListDataStore
import com.kotlinmvvm.data.response.TopHeadLinesNewsResponse


class NewsListRepository(private val newsListDataStore: NewsListDataStore) {

    fun getTopHeadLinesNews(country: String): LiveData<ResultState<TopHeadLinesNewsResponse>> {
        return newsListDataStore.getTopHeadLinesNews(country)
    }
}