package com.markettwits.waifupics.result


interface HandleNetworkResult {

    suspend fun <T : Any> tryRequest(request: suspend () -> T): NetworkResult<T>

    class Base(
        private val handleRequestCode: NetworkExceptionMapper,
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