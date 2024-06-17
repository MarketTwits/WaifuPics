package com.markettwits.random_image.data.cloud.exceptions

import kotlinx.serialization.SerializationException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ExceptionMapper {

    private val exceptionHandlers = mutableMapOf<Class<out Exception>, (Exception) -> String>()

    init {
        exceptionHandlers[SerializationException::class.java] = { e ->
            "Serialization Exception: ${e.message}"
        }
        exceptionHandlers[NetworkException.NoInternetException::class.java] = { e ->
            "No Internet: ${e.message}"
        }
        exceptionHandlers[NetworkException.TimeoutException::class.java] = { e ->
            "Timeout Exception: ${e.message}"
        }
        exceptionHandlers[SocketTimeoutException::class.java] = { e ->
            "Socket Timeout Exception, try again or later"
        }
        exceptionHandlers[SocketException::class.java] = { e ->
            "Socket Timeout Exception, try again or later"
        }
        exceptionHandlers[UnknownHostException::class.java] = { e ->
            "Network problem, try again or later"
        }
        exceptionHandlers[java.net.ConnectException::class.java] = { e ->
            "ConnectException, try again or later"
        }
    }

    fun handleNetworkException(exception: Exception): String {
        val handler = exceptionHandlers[exception.javaClass]
        return handler?.invoke(exception) ?: "Unhandled exception: ${exception.message}"
    }
}