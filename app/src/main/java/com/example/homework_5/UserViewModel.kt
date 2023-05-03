package com.example.homework_5

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dataclasses.UserResponse

class UserViewModel : ViewModel() {
    private val userRepository = UserRepository()

    fun getUsers(): LiveData<UserResponse> {
        return userRepository.getUsers()
    }
}