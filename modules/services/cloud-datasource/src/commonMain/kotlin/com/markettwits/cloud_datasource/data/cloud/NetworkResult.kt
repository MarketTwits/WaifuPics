package com.markettwits.cloud_datasource.data.cloud

sealed interface NetworkResult<out T> {

    interface Mapper<T, S>{
        fun map(item: T) : S
        fun map(errorMessage : String, code: Int) : S
    }

    fun <T,S> map(mapper : Mapper<T, S>) : S

    data class Success<T>(private val data: T) : NetworkResult<T> {
        @Suppress("UNCHECKED_CAST")
        override fun <T,S> map(mapper: Mapper<T, S>) = mapper.map(data as T)
    }

    data class Error(private val message: String = "", private val code: Int = 0) : NetworkResult<Nothing> {
        override fun <T,S> map(mapper: Mapper<T, S>) = mapper.map(message, code)
    }
}

