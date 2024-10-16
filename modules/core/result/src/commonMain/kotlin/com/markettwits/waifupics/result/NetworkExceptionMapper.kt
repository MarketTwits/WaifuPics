package com.markettwits.waifupics.result

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.JsonConvertException
import kotlin.toString

class NetworkExceptionMapper {

    fun handleNetworkException(exception: Exception): String {
        val networkException = mapThrowableToException(exception)
        return mapNetworkExceptionToString(networkException)
    }

    fun mapThrowableToException(throwable: Throwable): NetworkException {
        return when (throwable) {
            is JsonConvertException -> NetworkException.ParseJsonException(throwable.message.toString())
            is ClientRequestException -> if (throwable.response.status == HttpStatusCode.NotFound) {
                NetworkException.NotFoundRequest(throwable.message)
            } else {
                NetworkException.WrongRequest(throwable.message)
            }
            is ServerResponseException -> NetworkException.NoInternetException("Ooops, check your internet connection")
            else -> NetworkException.General()
        }
    }

    fun mapNetworkExceptionToString(exception: NetworkException): String {
        return when (exception) {
            is NetworkException.General -> "An unknown error occurred. Please try again later."
            is NetworkException.NoInternetException -> "No internet connection. Please check your network."
            is NetworkException.NotFoundRequest -> "The requested resource was not found. Please check the URL."
            is NetworkException.ParseJsonException -> "Error parsing data. Please check the server response format."
            is NetworkException.WrongRequest -> "Invalid request. Please check the parameters sent."
        }
    }
}