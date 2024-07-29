package com.markettwits.cloud_datasource.presentation.filter.domain

interface ProtectedMapper {

    fun map(list : List<String>) : Boolean

    class Base : ProtectedMapper {

        override fun map(list: List<String>): Boolean =
            list.containsAll(listOf("borderline", "explicit"))

    }
}