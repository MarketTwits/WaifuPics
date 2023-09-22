package com.markettwits.core.sl

import androidx.lifecycle.ViewModel

interface Module<T : ViewModel>{
   fun viewModel() : T
}