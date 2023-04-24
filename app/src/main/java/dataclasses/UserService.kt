package dataclasses

import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface UserService {
    @GET("users")
    fun getUsers(@Query("page") page: Int): Single<UserResponse>

    @GET("users/{userId}")
    fun getUser(@Path("userId") userId: Int): Single<UserUnic>

    @POST("/api/users")
    fun createUser(@Body userRequest: UserRequest): Single<NewUser>

}