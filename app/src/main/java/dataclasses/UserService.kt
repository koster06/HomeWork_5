package dataclasses

import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface UserService {
    @GET("users")
    fun getUsers(@Query("page") page: Int): Call<UserResponse>

    @GET("users")
    suspend fun getUsers1(@Query("page") page: Int): Response<UserResponse>

    @GET("users/{userId}")
    fun getUser(@Path("userId") userId: Int): Single<UserUnic>

    @POST("/api/users")
    fun createUser(@Body userRequest: UserRequest): Single<NewUser>

}