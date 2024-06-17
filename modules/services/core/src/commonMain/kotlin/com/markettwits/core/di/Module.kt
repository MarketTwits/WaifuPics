package com.markettwits.core.di

import androidx.lifecycle.ViewModel

interface Module<out T : ViewModel>{

   fun viewModel() : T

}