package com.kotlinmvvm.data.appnetwork

import androidx.lifecycle.MutableLiveData
import com.kotlinmvvm.data.AppExceptions.AppExceptions
import com.kotlinmvvm.data.AppResult
import com.kotlinmvvm.data.ResultState
import com.kotlinmvvm.data.getOrThrow
import com.kotlinmvvm.data.response.DataResponse
import kotlinx.coroutines.*
import retrofit2.Response
import kotlin.coroutines.resume

class CallHandler<RESPONSE : Any> {

    lateinit var client: Deferred<Response<RESPONSE>>

    fun getRemoteData(): MutableLiveData<ResultState<RESPONSE>> {

        val resultState = MutableLiveData<ResultState<RESPONSE>>()
        resultState.value = ResultState.LoadingState()
        GlobalScope.launch {
            kotlin.runCatching {
                client.getDataFromApi().getOrThrow() as DataResponse<RESPONSE>
            }.onFailure {
                withContext(Dispatchers.Main) {
                    resultState.value = ResultState.ErrorState(it as AppExceptions)
                }
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    resultState.value = ResultState.DataState(it.retrieveData())
                }
            }
        }
        return resultState
    }
}

suspend fun <T : Any> Deferred<Response<T>>.getDataFromApi(): AppResult<T> {

    return suspendCancellableCoroutine { cancellableCoroutine ->
        GlobalScope.launch {
            kotlin.runCatching {
                await()
            }.onFailure { cancellableCoroutine.resume(AppResult.EXCEPTION(it as AppExceptions)) }
                .onSuccess {
                    cancellableCoroutine.resume(
                        if (it.isSuccessful) {
                            getBodyOrThrowException(it)
                        } else {
                            AppResult.EXCEPTION(AppExceptions.HttpException())
                        }
                    )
                }
        }
    }
}

private fun <T : Any> getBodyOrThrowException(response: Response<T>?): AppResult<T> {
    val body = response!!.body()
    return body?.let {
        AppResult.SUCCESS(it)
    } ?: "error".let {
        if (response.code() == 200) {
            AppResult.EXCEPTION(AppExceptions.EmptyStateException())
        } else {
            AppResult.EXCEPTION(AppExceptions.NullPointerException())
        }
    }
}
