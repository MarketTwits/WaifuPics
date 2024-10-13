package com.markettwits.cloud_datasource.data.cloud.exceptions

sealed class NetworkException(message: String) : Exception(message) {

    class NoInternetException(message: String) : NetworkException(message)
    class TimeoutException(message: String) : NetworkException(message)
}