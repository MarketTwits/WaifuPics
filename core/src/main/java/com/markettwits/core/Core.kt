package com.markettwits.core
import android.content.Context
import com.markettwits.core.storage.SharedPreferencesStorage
import com.markettwits.core.wrappers.ManageResource


class Core(
    private val context: Context
) : ProvideStorage, ProvideManageResource {
    private val manageResource = ManageResource.Base(context)
    private val sharedPreferencesStorage =
        SharedPreferencesStorage.Base(context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE))
    fun context() = context
    override fun storage(): SharedPreferencesStorage = sharedPreferencesStorage
    override fun manageResource(): ManageResource = manageResource
    companion object {
        private const val STORAGE_NAME = "NEWS APP DATA"
    }
}

interface ProvideStorage {
    fun storage(): SharedPreferencesStorage
}

interface ProvideManageResource {
    fun manageResource(): ManageResource
}
