package com.example.free

import com.example.lib.UserResponseLib
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface UserServiceFree {
    @GET
    suspend fun getUsers(@Url url: String) : Response<UserResponseLib>

}