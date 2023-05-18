package repository

import com.example.lib.UserResponseLib
import com.example.lib.UserServiceLib

class UserRepository(private val userService: UserServiceLib)  {

    suspend fun getUsers1(): UserResponseLib {
        return userService.getUsers1("users?page=1").body()!!
    }
}

