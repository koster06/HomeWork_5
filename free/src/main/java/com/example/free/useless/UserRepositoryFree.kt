package com.example.free.useless

import com.example.lib.UserLib

class UserRepositoryFree(private val userService: UserServiceFree) {

    suspend fun getUsers(): List<UserLib> {
        val url = "https://reqres.in/api/users"
        val response = userService.getUsers(url)
        if (response.isSuccessful) {
            val userResponse = response.body()
            return userResponse?.data ?: emptyList()
        } else {
            throw Exception("Failed to get users: ${response.message()}")
        }
    }
}
