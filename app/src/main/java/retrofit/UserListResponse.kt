package retrofit

import com.example.homework_5.User

data class UserListResponse (
    val page: Int,
    val perPage: Int,
    val total: Int,
    val totalPages: Int,
    val data: List<User>
)
