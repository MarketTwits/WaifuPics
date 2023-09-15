package com.markettwits.waifupics.view.main.ui.image

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markettwits.waifupics.core.communication.Communication
import com.markettwits.waifupics.view.main.data.net.NekoService
import com.markettwits.waifupics.view.main.data.net.models.RandomImageCloud
import kotlinx.coroutines.launch

class ImageViewModel(
    //private val communication: ImageCommunication,
    private val service: NekoService
) : ViewModel(),
    Communication.Observe<NetworkState<RandomImageCloud>> {
    val state = MutableLiveData<NetworkState<RandomImageCloud>>()
    init {
        state.value = NetworkState.Loading()
        fetchRandomImage()
    }
    fun fetchRandomImage(){
        try {
            viewModelScope.launch {
                state.value = NetworkState.Loading()
                val a = service.randomImage()
                state.value = NetworkState.Success(a)
                Log.d("mt05", a.data.toString())
            }
        }catch (e : Exception){
            Log.d("mt05", e.localizedMessage)
        }
    }

}
sealed class NetworkState<T>(data : T? = null){
    data class Success<T>(val data: T?) : NetworkState<T>(data)
    class Loading<T> : NetworkState<T>(null)
}


interface ImageCommunication : Communication.Mutable<NetworkState<RandomImageCloud>> {
    class Base : Communication.Abstract<NetworkState<RandomImageCloud>>(), ImageCommunication
}



