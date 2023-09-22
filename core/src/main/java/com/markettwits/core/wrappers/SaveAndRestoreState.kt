package com.markettwits.core.wrappers

import android.os.Build
import android.os.Bundle
import android.os.Parcelable

interface SaveAndRestoreState {

    interface Save<T : Parcelable> {
        fun save(data: T)
    }

    interface Restore<T : Parcelable> : IsEmpty {
        fun restore(): T
    }

    interface Mutable<T : Parcelable> : Save<T>, Restore<T>

    abstract class Abstract<T : Parcelable>(
        private val bundle: Bundle?,
        private val key: String,
        private val clazz: Class<T>
    ) : Mutable<T> {
        override fun save(data: T) = bundle!!.putParcelable(key, data)

        override fun restore(): T =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                bundle!!.getParcelable(key, clazz)!!
            else
                bundle!!.getParcelable<T>(key)!!

        override fun isEmpty() = bundle == null
    }
}
