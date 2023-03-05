package com.example.homework_5

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable


data class User(
    val id:Int,
    val name:String,
    val secName: String,
    val phone: String,
    val age: String,
    val birthday:String
    ) : java.io.Serializable
