package com.example.free

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lib.UserLib
import kotlinx.coroutines.launch

class UserViewModelFree(private val userRepository: UserRepositoryFree) : ViewModel() {

        private val _users = MutableLiveData<List<UserLib>>()
        val users: LiveData<List<UserLib>>
            get() = _users

        init {
            viewModelScope.launch {
                try {
                    _users.value = userRepository.getUsers()
                } catch (e: Exception) {
                    // errrorrs
                }
            }
        }
}
