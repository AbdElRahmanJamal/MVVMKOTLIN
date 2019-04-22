package com.kotlinmvvm.data.datastore

import com.kotlinmvvm.data.appnetwork.NewsAPIs
import com.kotlinmvvm.data.appnetwork.getRemoteData
import com.kotlinmvvm.data.response.TopHeadLinesNewsResponse

class NewsListDataStore(private val newsAPIs: NewsAPIs) {
    fun getTopHeadLinesNews(country: String) = getRemoteData<TopHeadLinesNewsResponse> {
        client = newsAPIs.getTopHeadLinesNews(country)
    }
}