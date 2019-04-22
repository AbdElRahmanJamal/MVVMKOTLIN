package com.kotlinmvvm.UI.newslistUI

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kotlinmvvm.data.ResultState
import com.kotlinmvvm.data.repository.NewsListRepository
import com.kotlinmvvm.data.response.TopHeadLinesNewsResponse

class NewsListViewModel(private val newsListRepository: NewsListRepository) : ViewModel() {

    suspend fun getTopHeadLinesNews(country: String): LiveData<ResultState<TopHeadLinesNewsResponse>> {
        return newsListRepository.getTopHeadLinesNews(country)
    }
}
