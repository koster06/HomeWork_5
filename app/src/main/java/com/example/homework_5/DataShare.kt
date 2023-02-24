package com.example.homework_5

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

open class DataShare: ViewModel() { // I choose this variant for communicate between frags cos it more comfortable for me, and I used it later

    val shareForFragment1: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val shareForActivity: MutableLiveData<Int> = MutableLiveData()  //-- the same

    val shareForFragment2: MutableLiveData<String> = MutableLiveData()

    val shareForFragment1Date: MutableLiveData<Date> by lazy {
        MutableLiveData<Date>()
    }

    val shareForFragment1Boolean: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }



}