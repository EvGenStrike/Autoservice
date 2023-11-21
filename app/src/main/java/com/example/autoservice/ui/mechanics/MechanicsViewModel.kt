package com.example.autoservice.ui.mechanics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MechanicsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Механики"
    }
    val text: LiveData<String> = _text
}