package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ArActivityViewModel(text: String) : ViewModel() {

    val sourceText = MutableLiveData<String>()
    val countValue : LiveData<String>
        get() = sourceText

    init {
        sourceText.value = "Real time text"
    }

    fun  updateText(value: String?) {
        sourceText.value += "\n" + value
    }
}