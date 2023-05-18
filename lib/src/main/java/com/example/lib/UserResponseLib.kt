package com.example.lib

data class UserResponseLib(
    val page: Int,
    val perPage: Int,
    val total: Int,
    val totalPages: Int,
    val data: List<UserLib>
)
