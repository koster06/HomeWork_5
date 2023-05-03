package com.example.homework_5

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dataclasses.UserResponse
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val userRepository = UserRepository()
    private val _users = MutableLiveData<UserResponse>()

//    fun getUsers(): LiveData<UserResponse> {
//        return userRepository.getUsers()
//    }

    val users: LiveData<UserResponse>
        get() = _users

    init {
        viewModelScope.launch {
            _users.value = userRepository.getUsers1()
        }
    }

}