package com.kotlinmvvm.data.AppExceptions

import java.io.IOException

sealed class AppExceptions : IOException() {
    class NoConnectivityException : AppExceptions()
    class GenericException : AppExceptions()
    class NullPointerException : AppExceptions()
    class EmptyStateException : AppExceptions()
    class HttpException : AppExceptions()
}