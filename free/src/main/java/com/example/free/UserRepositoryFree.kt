package com.example.free

class UserRepositoryFree(private val userService: UserServiceFree) {

    suspend fun getUsers(): List<UserFree> {
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
