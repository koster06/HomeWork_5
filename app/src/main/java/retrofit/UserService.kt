package retrofit

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
//    @GET("users")
//    suspend fun getUsers(@Query("page") page: Int): Response<UserListResponse>

    @GET("users")
    suspend fun getUsers(@Query("page") page: Int): Users

    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: Int): UserResponse


    @GET("products")
    suspend fun getAllProducts(): Products

    @GET("products")
    suspend fun getProduct (@Path("id")id: Int): Product

}