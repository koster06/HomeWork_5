package repository

import dataclasses.UserResponse
import dataclasses.UserService

class UserRepository(private val userService: UserService)  {

    suspend fun getUsers1(): UserResponse {
        return userService.getUsers1("users?page=1").body()!!
    }
}

