package dataclasses

import com.example.lib.UserResponseLib
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.*

interface UserService {
    @GET
    suspend fun getUsers1(@Url url: String) : Response<UserResponseLib>

    @GET("users/{userId}")
    fun getUser(@Path("userId") userId: Int): Single<UserUnic>

    @POST("/api/users")
    fun createUser(@Body userRequest: UserRequest): Single<NewUser>

}