package dataclasses

data class UserResponse(
    val page: Int,
    val perPage: Int,
    val total: Int,
    val totalPages: Int,
    val data: List<User>
)
