package com.markettwits.core.wrappers

import android.os.Build
import android.os.Bundle
import android.os.Parcelable

interface WrapBundle {
    fun save(key : String, parcelable: Parcelable)
    fun <T>read(key: String, clazz: Class<T>) : T
    class Base(private val bundle: Bundle) : WrapBundle{
        override fun save(key: String, parcelable: Parcelable) {
            bundle.putParcelable(key, parcelable)
        }
        override fun <T>read(key: String,clazz: Class<T>): T {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable(key, clazz) as T
            } else {
                bundle.getParcelable(key)!!
            }
        }
    }
}