package com.markettwits.waifupics.core.wrappers

import android.content.Context

interface ManageResource {
    fun string(id : Int) : String
    fun color(id : Int) : Int
    class Base(private val context: Context) : ManageResource {
        override fun string(id: Int) = context.getString(id)
        override fun color(id: Int): Int = context.getColor(id)
    }
}