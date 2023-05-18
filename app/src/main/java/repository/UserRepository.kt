package repository

import com.example.lib.UserResponseLib
import dataclasses.UserService

class UserRepository(private val userService: UserService)  {

    suspend fun getUsers1(): UserResponseLib {
        return userService.getUsers1("users?page=1").body()!!
    }
}

