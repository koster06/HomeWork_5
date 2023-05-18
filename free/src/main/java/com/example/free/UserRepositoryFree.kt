package com.example.free

import com.example.lib.UserLib
import com.example.lib.UserServiceLib

class UserRepositoryFree(private val userService: UserServiceLib) {

    suspend fun getUsers(): List<UserLib> {
        val url = "https://reqres.in/api/users"
        val response = userService.getUsers1(url)
        if (response.isSuccessful) {
            val userResponse = response.body()
            return userResponse?.data ?: emptyList()
        } else {
            throw Exception("Failed to get users: ${response.message()}")
        }
    }
}
