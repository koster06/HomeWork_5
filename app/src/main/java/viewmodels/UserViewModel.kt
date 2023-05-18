package viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lib.UserResponseLib
import kotlinx.coroutines.launch
import repository.UserRepository

class UserViewModel (private val userRepository: UserRepository) : ViewModel() {

    private val _users = MutableLiveData<UserResponseLib>()

    val users: LiveData<UserResponseLib>
        get() = _users

    init {
        viewModelScope.launch {
            _users.value = userRepository.getUsers1()
        }
    }
}