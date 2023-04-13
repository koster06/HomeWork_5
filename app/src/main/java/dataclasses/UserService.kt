package dataclasses

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET("users")
    fun getUsers(@Query("page") page: Int): Single<UserResponse>
}