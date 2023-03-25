package retrofit

import retrofit2.http.GET

data class UserResponse(
    val data: User,
    val support: Support
)


