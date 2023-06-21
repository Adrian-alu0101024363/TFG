package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ArActivityViewModelFactory(private val startingCount : String) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArActivityViewModel::class.java)){
            return ArActivityViewModel(startingCount) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}
