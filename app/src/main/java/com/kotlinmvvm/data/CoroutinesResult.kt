package com.kotlinmvvm.data

import com.kotlinmvvm.data.AppExceptions.AppExceptions

sealed class AppResult<out T : Any> {

    class SUCCESS<out T : Any>(val value: T) : AppResult<T>()
    class EXCEPTION(val exception: AppExceptions) : AppResult<Nothing>()
}

fun <T : Any> AppResult<T>.getOrThrow(throwable: Throwable? = null): T {
    return when (this) {
        is AppResult.SUCCESS -> value
        is AppResult.EXCEPTION -> throw throwable ?: exception
    }
}