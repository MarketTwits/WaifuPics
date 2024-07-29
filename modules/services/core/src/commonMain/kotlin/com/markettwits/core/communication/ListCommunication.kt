package com.markettwits.core.communication

interface ListCommunication{

    interface Mutable<T : Any> : ListCommunication {

        fun remove(item : T)

        fun add(item : T)

        fun fetch() : List<T>

        fun clear()
    }
    abstract class Abstract<T : Any>(private val list: MutableList<T>) : Mutable<T> {
        override fun remove(item: T) {
            list.remove(item)
        }

        override fun add(item: T) {
            list.add(item)
        }

        override fun fetch(): MutableList<T>  = list

        override fun clear() {
            list.clear()
        }
    }
    class Base<T : Any>(private val list : MutableList<T>) : Abstract<T>(list)
}