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

    fun getUsers(): MutableLiveData<UserResponse> {
        val data = MutableLiveData<UserResponse>()

        userService.getUsers(2).enqueue( object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    data.value = response.body()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {

            }
        })

        return data
    }

    suspend fun getUsers1(): UserResponse {
        return userService.getUsers1(1).body()!!
    }

}

