package com.kotlinmvvm.data

import com.kotlinmvvm.data.AppExceptions.AppExceptions


sealed class ResultState<out T : Any> {

    class DataState<out T : Any>(val value: T) : ResultState<T>()
    class ErrorState(val exception: AppExceptions) : ResultState<Nothing>()
    class LoadingState() : ResultState<Nothing>()

}