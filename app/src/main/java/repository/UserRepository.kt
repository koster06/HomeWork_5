package repository

import com.example.homework_5.UserService
import com.example.lib.UserResponseLib

class UserRepository(private val userService: UserService)  {

    suspend fun getUsers1(): UserResponseLib {
        return userService.getUsers1("users?page=1").body()!!
    }
}

