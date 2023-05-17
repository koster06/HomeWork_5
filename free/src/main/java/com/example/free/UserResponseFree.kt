package com.example.free

data class UserResponseFree(
    val page: Int,
    val perPage: Int,
    val total: Int,
    val totalPages: Int,
    val data: List<UserFree>
)
