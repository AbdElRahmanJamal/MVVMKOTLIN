package com.kotlinmvvm.data.response

import com.google.gson.annotations.SerializedName

data class TopHeadLinesNewsResponse(
        @SerializedName("articles")
        val articles: List<Article>,
        @SerializedName("status")
        val status: String,
        @SerializedName("totalResults")
        val totalResults: Int
) : DataResponse<TopHeadLinesNewsResponse> {
    override fun retrieveData() = this

}

interface DataResponse<T> {
    fun retrieveData(): T
}