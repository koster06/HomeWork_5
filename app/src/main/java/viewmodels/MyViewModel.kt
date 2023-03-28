package viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.homework_5.UserRepository
import entities.AddressEntity
import entities.UserEntity

class MyViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository: UserRepository = UserRepository(application)

    // Получить всех пользователей из БД
    fun getAllUsers(): LiveData<List<UserEntity>> {
        return userRepository.getAllUsers()
    }

    // Добавить нового пользователя в БД
    fun addUser(user: UserEntity) {
        userRepository.addUser(user)
    }

    // Удалить пользователя из БД
    fun deleteUser(user: UserEntity) {
        userRepository.deleteUser(user)
    }

    // Обновить данные пользователя в БД
    fun updateUser(user: UserEntity) {
        userRepository.updateUser(user)
    }

    // Получить все адреса из БД
    fun getAllAddresses(): LiveData<List<AddressEntity>> {
        return userRepository.getAllAddresses()
    }
}