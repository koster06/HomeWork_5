package com.example.homework_5

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

open class DataShare: ViewModel() {

    val shareForFragment1: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val shareForActivity: MutableLiveData<Boolean> = MutableLiveData()  //-- the same

    val shareForFragment2: MutableLiveData<String> = MutableLiveData()

    val shareForFragment1Date: MutableLiveData<Date> by lazy {
        MutableLiveData<Date>()
    }

    val shareForFragment1Boolean: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }



}