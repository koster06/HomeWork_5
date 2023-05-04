package com.example.homework_5

import androidx.lifecycle.MutableLiveData
import dataclasses.UserResponse
import dataclasses.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserRepository {

    private val userService = Retrofit.Builder()
        .baseUrl("https://reqres.in/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(UserService::class.java)

    suspend fun getUsers1(): UserResponse {
        return userService.getUsers1(1).body()!!
    }
}

