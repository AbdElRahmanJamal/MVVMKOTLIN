package com.kotlinmvvm.data.appnetwork

import com.kotlinmvvm.data.response.TopHeadLinesNewsResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPIs {

    @GET("v2/top-headlines")
    fun getTopHeadLinesNews(
        @Query("country") country: String
    ): Deferred<Response<TopHeadLinesNewsResponse>>
}