package com.example.homework_5


data class User( //data class for creating User instance
    val id:Int,
    val name:String,
    val secName: String,
    val phone: String,
    val age: String,
    val birthday:String
    ) : java.io.Serializable
