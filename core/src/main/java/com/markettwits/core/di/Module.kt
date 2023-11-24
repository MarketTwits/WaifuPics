package com.markettwits.core.di

import androidx.lifecycle.ViewModel

interface Module<T : ViewModel>{
   fun viewModel() : T
}