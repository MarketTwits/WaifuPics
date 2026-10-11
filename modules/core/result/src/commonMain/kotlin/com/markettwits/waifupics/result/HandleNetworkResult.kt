package com.markettwits.waifupics.result

import kotlinx.coroutines.CancellationException


interface HandleNetworkResult {

    suspend fun <T : Any> tryRequest(request: suspend () -> T): NetworkResult<T>

    class Base(
        private val handleRequestCode: NetworkExceptionMapper,
    ) : HandleNetworkResult {

        override suspend fun <T : Any> tryRequest(request: suspend () -> T): NetworkResult<T> {
            return try {
                NetworkResult.Success(request.invoke())
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                println("HandleNetworkResult : ${e.message}")
                NetworkResult.Error(handleRequestCode.handleNetworkException(e))
            }
        }
    }
}
