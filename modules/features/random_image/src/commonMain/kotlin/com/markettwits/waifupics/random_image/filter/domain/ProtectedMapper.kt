package com.markettwits.waifupics.random_image.filter.domain

interface ProtectedMapper {

    fun map(list : List<String>) : Boolean

    class Base : ProtectedMapper {

        override fun map(list: List<String>): Boolean =
            list.containsAll(listOf("borderline", "explicit"))

    }
}