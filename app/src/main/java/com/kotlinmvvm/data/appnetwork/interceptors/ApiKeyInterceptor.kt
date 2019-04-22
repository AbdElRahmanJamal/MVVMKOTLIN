package com.kotlinmvvm.data.appnetwork.interceptors

import okhttp3.Interceptor
import okhttp3.Response

const val API_KEY = "2b77b0c7a6ea4e5daca90337c443422f"

class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request()
            .url()
            .newBuilder()
            .addQueryParameter("apiKey", API_KEY)
            .build()
        val request = chain.request()
            .newBuilder()
            .url(url)
            .build();
        return chain.proceed(request);
    }
}