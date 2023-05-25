package com.example.free.useless

import com.example.lib.NewUser
import com.example.lib.UserLib
import com.example.lib.UserRequest
import com.example.lib.UserResponseLib
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Url

interface UserServiceFree {
    @GET
    suspend fun getUsers(@Url url: String) : Response<UserResponseLib>

    @POST("/api/users")
    fun createUser(@Body userRequest: UserRequest): Single<NewUser>

    @GET("users/{userId}")
    fun getUser(@Path("userId") userId: Int): Response<UserLib>

}