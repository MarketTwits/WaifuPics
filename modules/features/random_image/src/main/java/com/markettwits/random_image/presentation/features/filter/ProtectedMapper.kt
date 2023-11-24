package com.markettwits.random_image.presentation.features.filter

interface ProtectedMapper {
    fun map(list : List<String>) : Boolean
    class Base : ProtectedMapper {
        override fun map(list: List<String>): Boolean {
            return list.containsAll(listOf("borderline", "explicit"))
        }
    }
}