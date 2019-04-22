package com.kotlinmvvm.data.appnetwork

import androidx.lifecycle.MutableLiveData
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.kotlinmvvm.data.ResultState
import com.kotlinmvvm.data.appnetwork.interceptors.ApiKeyInterceptor
import com.kotlinmvvm.data.appnetwork.interceptors.IConnectivityInterceptor
import com.kotlinmvvm.data.response.DataResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//v2/top-headlines?country=us&apiKey=2b77b0c7a6ea4e5daca90337c443422f
const val BASE_URL = "https://newsapi.org/"

object RetrofitCreator {
    operator fun invoke(iConnectivityInterceptor: IConnectivityInterceptor): NewsAPIs {
        val okHttp = OkHttpClient.Builder()
                .addInterceptor(iConnectivityInterceptor)
                .addInterceptor(ApiKeyInterceptor())
                .build()

        return Retrofit.Builder()
                .client(okHttp)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NewsAPIs::class.java)
    }
}

fun <RESPONSE : DataResponse<*>> getRemoteData(block: CallHandler<RESPONSE>.() -> Unit)
        : MutableLiveData<ResultState<RESPONSE>> = CallHandler<RESPONSE>().apply(block).getRemoteData()