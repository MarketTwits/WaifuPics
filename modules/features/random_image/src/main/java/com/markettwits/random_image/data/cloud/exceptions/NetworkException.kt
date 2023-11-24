package com.markettwits.random_image.data.cloud.exceptions

sealed class NetworkException(message: String) : Exception(message) {
    class SerializationException(message: String) : NetworkException(message)
    class NoInternetException(message: String) : NetworkException(message)
    class TimeoutException(message: String) : NetworkException(message)
}