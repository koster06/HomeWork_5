package com.example.homework_5

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class DataShare: ViewModel() {

    val shareForFragment1: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }



}