package com.markettwits.core
import android.content.Context
import com.markettwits.core.storage.SharedPreferencesStorage
import com.markettwits.core.wrappers.DispatchersList
import com.markettwits.core.wrappers.ManageResource
import com.markettwits.core.wrappers.SaveAndRestoreState


class Core(
    private val context: Context,
    private val saveAndRestoreState: SaveAndRestoreState
) : ProvideStorage, ProvideManageResource,ProvideBundleWrapper, ProvideDispatchersList {
    private val manageResource = ManageResource.Base(context)
    private val sharedPreferencesStorage =
        SharedPreferencesStorage.Base(context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE))
    private val dispatchersList = DispatchersList.Base()
    fun context() = context
    override fun storage(): SharedPreferencesStorage = sharedPreferencesStorage
    override fun manageResource(): ManageResource = manageResource

    override fun bundleWrapper() = saveAndRestoreState

    override fun dispatchers(): DispatchersList = dispatchersList
    companion object {
        private const val STORAGE_NAME = "NEWS APP DATA"
    }
}
interface ProvideDispatchersList{
    fun dispatchers() : DispatchersList
}

interface ProvideStorage {
    fun storage(): SharedPreferencesStorage
}

interface ProvideManageResource {
    fun manageResource(): ManageResource
}
interface ProvideBundleWrapper{
    fun bundleWrapper() : SaveAndRestoreState
}
