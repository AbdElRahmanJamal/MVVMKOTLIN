package com.kotlinmvvm.data.appnetwork.interceptors

import android.content.Context
import android.net.ConnectivityManager
import com.kotlinmvvm.data.AppExceptions.AppExceptions
import okhttp3.Interceptor
import okhttp3.Response

interface IConnectivityInterceptor : Interceptor

class ConnectivityInterceptor(context: Context) : IConnectivityInterceptor {

    val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isOnline()) throw AppExceptions.NoConnectivityException()
        return chain.proceed(chain.request())
    }

    private fun isOnline(): Boolean {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
