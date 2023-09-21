package com.markettwits.waifupics.view.filter.presentation

interface FilterState{
    fun isOpened() : Boolean
    fun filterValue() : List<FilterItem>
    abstract class Abstract(private val value : List<FilterItem>) : FilterState{
        override fun filterValue() = value
    }
    data class Opened(private val value : List<FilterItem>): Abstract(value){
        override fun isOpened() =  true
    }
    data class Close(private val value : List<FilterItem>) : Abstract(value){
        override fun isOpened() = false
    }
    object Initial : FilterState {
        override fun isOpened()= false
        override fun filterValue() = emptyList<FilterItem>()
    }
}