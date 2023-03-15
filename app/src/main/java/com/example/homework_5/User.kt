package com.example.homework_5


data class User(
    val id:Int,
    val name:String,
    val secName: String,
    val phone: String,
    val age: String,
    val birthday:String,
    val image: Int
    ) : java.io.Serializable
