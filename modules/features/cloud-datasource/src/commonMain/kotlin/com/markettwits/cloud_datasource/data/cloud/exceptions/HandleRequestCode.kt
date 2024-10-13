package com.markettwits.cloud_datasource.data.cloud.exceptions

class ExceptionMapper {

   // private val exceptionHandlers = mutableMapOf<Class<out Exception>, (Exception) -> String>()

    init {
//        exceptionHandlers[SerializationException::class] = { e ->
//            "Serialization Exception: ${e.message}"
//        }
//        exceptionHandlers[NetworkException.NoInternetException::class] = { e ->
//            "No Internet: ${e.message}"
//        }
//        exceptionHandlers[NetworkException.TimeoutException::class.java] = { e ->
//            "Timeout Exception: ${e.message}"
//        }
//        exceptionHandlers[SocketTimeoutException::class.java] = { e ->
//            "Socket Timeout Exception, try again or later"
//        }
//        exceptionHandlers[SocketException::class.java] = { e ->
//            "Socket Timeout Exception, try again or later"
//        }
//        exceptionHandlers[UnknownHostException::class.java] = { e ->
//            "Network problem, try again or later"
//        }
//        exceptionHandlers[java.net.ConnectException::class.java] = { e ->
//            "ConnectException, try again or later"
//        }
    }

    fun handleNetworkException(exception: Exception): String {
     //   val handler = exceptionHandlers[exception.javaClass]
        return exception.message.toString()
    }
}