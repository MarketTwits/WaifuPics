package com.markettwits.waifupics.core.sl

import androidx.lifecycle.ViewModel

interface Module<T : ViewModel>{
   fun viewModel() : T

}