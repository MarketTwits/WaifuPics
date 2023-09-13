package com.markettwits.waifupics.core.wrappers

import android.util.Log

interface Logger {
    fun logDebug(log: String = "coreD", message: String)
    fun logError(log: String = "coreE", message: String, throwable: Throwable? = null)
    class Base() : Logger {
        override fun logDebug(log: String, message: String) {
            Log.d(log, message)
        }

        override fun logError(log: String, message: String, throwable: Throwable?) {
            Log.e(log, message, throwable)
        }
    }

    class Console() : Logger {
        override fun logDebug(log: String, message: String) {
            println("Log : $log, Message: $message")
        }

        override fun logError(log: String, message: String, throwable: Throwable?) {
            val red = "\u001b[31m"
            println(red + "Log : $log, Message : $message, Throw: $throwable")
        }
    }
}