package com.kotlinmvvm

import android.app.Application
import com.kotlinmvvm.UI.newslistUI.NewsListViewModelFactory
import com.kotlinmvvm.data.appnetwork.RetrofitCreator
import com.kotlinmvvm.data.appnetwork.interceptors.ConnectivityInterceptor
import com.kotlinmvvm.data.appnetwork.interceptors.IConnectivityInterceptor
import com.kotlinmvvm.data.datastore.NewsListDataStore
import com.kotlinmvvm.data.repository.NewsListRepository
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class KotlinMVVM : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@KotlinMVVM))//to return  instance of context or anything related to android

        bind<IConnectivityInterceptor>() with singleton { ConnectivityInterceptor(instance()) }
        bind() from singleton { RetrofitCreator(instance()) }
        bind() from singleton { NewsListDataStore(instance()) }
        bind() from singleton { NewsListRepository(instance()) }
        bind() from singleton { NewsListViewModelFactory(instance()) }
    }
}