package com.example.lib

import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Url

interface UserServiceLib {
    suspend fun getUsers1(@Url url: String) : Response<UserResponseLib>

    @GET("users/{userId}")
    fun getUser(@Path("userId") userId: Int): Single<UserUnicLib>

    @POST("/api/users")
    fun createUser(@Body userRequest: UserRequestLib): Single<NewUserLib>
}