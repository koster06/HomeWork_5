package com.example.free.useless

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UserViewModelFactoryFree(private val userRepository: UserRepositoryFree) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModelFree::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModelFree(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}