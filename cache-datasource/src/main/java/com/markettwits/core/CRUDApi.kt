package com.markettwits.core

interface CRUDApi{
    interface Write<T>{
        fun write(data : T) = Unit
    }
    interface Read<T>{
        interface Single<T> : Read<T>{
            fun read(id : Int) : T
        }
        interface All<T> : Read<T>{
            fun read() : List<T>
        }
        interface Mutable<T> : Single<T>, All<T>
    }
    interface Update<T>{
        fun update(data : T) = Unit
        fun update(id : Int)
    }
    interface Delete<T>{
        fun delete(data : T) = Unit
        fun delete(id : Int) = Unit
    }
    interface Mutable<T> : Write<T>, Read<T>, Update<T>, Delete<T>
}