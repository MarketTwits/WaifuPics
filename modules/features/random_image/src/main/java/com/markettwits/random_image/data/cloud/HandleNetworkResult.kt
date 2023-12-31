package com.markettwits.random_image.data.cloud

import com.markettwits.random_image.data.cloud.exceptions.ExceptionMapper


interface HandleNetworkResult {
    suspend fun <T : Any> tryRequest(request: suspend () -> T): NetworkResult<T>
    class Base(
        private val handleRequestCode: ExceptionMapper,
    ) : HandleNetworkResult {
        override suspend fun <T : Any> tryRequest(request: suspend () -> T): NetworkResult<T> {
            return try {
                NetworkResult.Success(request.invoke())
            } catch (e: Exception) {
                NetworkResult.Error(handleRequestCode.handleNetworkException(e))
            }
        }
    }
}