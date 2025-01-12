package com.markettwits.waifupics.result

sealed class NetworkException(message: String) : Throwable(message) {

    class NoInternetException(message: String) : NetworkException(message)

    class WrongRequest(message: String) : NetworkException(message)

    class NotFoundRequest(message: String) : NetworkException(message)

    class ParseJsonException(message: String) : NetworkException(message)

    class General() : NetworkException("")
}

