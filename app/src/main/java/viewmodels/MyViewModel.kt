package viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.homework_5.UserRepository
import entities.AddressEntity
import entities.UserEntity

class MyViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository: UserRepository = UserRepository(application)
    val messageForActivity: MutableLiveData<AddressEntity> by lazy {
        MutableLiveData<AddressEntity>()
    }

    val messageForCloseFragment: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun getAllUsers(): LiveData<List<UserEntity>> {
        return userRepository.getAllUsers()
    }

    fun addUser(user: UserEntity) {
        userRepository.addUser(user)
    }

    fun deleteUser(user: UserEntity) {
        userRepository.deleteUser(user)
    }

    fun updateUser(user: UserEntity) {
        userRepository.updateUser(user)
    }

    fun getAllAddresses(): LiveData<List<AddressEntity>> {
        return userRepository.getAllAddresses()
    }
}