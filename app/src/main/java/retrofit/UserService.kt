package retrofit

import retrofit2.http.*

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

    @POST("/api/users")
    suspend fun createUser(@Body userRequest: UserRequest): UserResponsePost

}