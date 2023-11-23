package com.markettwits.random_image.data.cloud

import com.markettwits.random_image.data.cloud.exceptions.NetworkException

class ExceptionMapper {
    private val exceptionHandlers = mutableMapOf<Class<out Exception>, (Exception) -> String>()

    init {
        // Register handlers for specific exception types
        exceptionHandlers[NetworkException.SerializationException::class.java] = { e ->
            "Serialization Exception: ${e.message}"
        }
        exceptionHandlers[NetworkException.NoInternetException::class.java] = { e ->
            "No Internet: ${e.message}"
        }
        exceptionHandlers[NetworkException.TimeoutException::class.java] = { e ->
            "Timeout Exception: ${e.message}"
        }
        // Add more handlers as needed
    }

    fun handleNetworkException(exception: Exception): String {
        val handler = exceptionHandlers[exception.javaClass]
        return handler?.invoke(exception) ?: "Unhandled exception: ${exception.message}"
    }
}