package viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_5.UserRepository
import dataclasses.UserResponse
import kotlinx.coroutines.launch

class UserViewModel (private val userRepository: UserRepository) : ViewModel() {

    private val _users = MutableLiveData<UserResponse>()

    val users: LiveData<UserResponse>
        get() = _users

    init {
        viewModelScope.launch {
            _users.value = userRepository.getUsers1()
        }
    }
}