package com.example.fintechtinkoff2023.core.storage

import android.content.SharedPreferences

interface SharedPreferencesStorage {
    fun save(key: String, value: Boolean)
    fun save(key: String, value: String)
    fun read(key: String, default: Boolean): Boolean
    fun read(key: String, default: String): String
    class Base(private val sharedPreferences: SharedPreferences) : SharedPreferencesStorage {
        override fun save(key: String, value: Boolean) {
            sharedPreferences.edit().putBoolean(key, value).apply()
        }

        override fun save(key: String, value: String) {
            sharedPreferences.edit().putString(key, value).apply()
        }

        override fun read(key: String, default: Boolean): Boolean =
            sharedPreferences.getBoolean(key, default)

        override fun read(key: String, default: String): String =
            sharedPreferences.getString(key, default) ?: default
    }
}