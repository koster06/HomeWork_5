package com.example.homework_5

import com.example.lib.UserLib
import com.example.lib.UserResponseLib
import dataclasses.NewUser
import dataclasses.UserRequest
import dataclasses.UserUnic
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import repository.UserRepository
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class UserRepositoryTest {

    private val mockUserService: UserService = mock(UserService::class.java)
    private lateinit var mockWebServer: MockWebServer
    private lateinit var userService: UserService
    private val userRepository = UserRepository(mockUserService)

    @Test
    fun getUsers1_returnsExpectedUserResponseLib() = runBlocking {
        val expectedUserResponseLib = UserResponseLib(
            page = 1,
            perPage = 10,
            total = 100,
            totalPages = 10,
            data = listOf(
                UserLib(1, "test1@example.com", "John", "Doe", "avatar1"),
                UserLib(2, "test2@example.com", "Jane", "Smith", "avatar2")
            )
        )

        `when`(mockUserService.getUsers1("users?page=1")).thenReturn(Response.success(expectedUserResponseLib))

        val result = userRepository.getUsers1()

        assertEquals(expectedUserResponseLib, result)
    }

    @Test
    fun getUser_returnsExpectedUserUnic() {
        val expectedUserLib = UserLib(1, "test@example.com", "John", "Doe", "avatar")
        val expectedUserUnic = UserUnic(expectedUserLib)

        val userService = mock(UserService::class.java)

        `when`(userService.getUser(1)).thenReturn(Single.just(expectedUserUnic))

        val result = userService.getUser(1).blockingGet()

        assertEquals(expectedUserUnic, result)
    }

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        userService = retrofit.create(UserService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun createUser_returnsExpectedNewUser() {

        val userRequest = UserRequest("John", "MicroSoft")
        val expectedNewUser = NewUser("John", "Doe", "1", "2023-05-22")

        val responseBody = """
            {
                "name": "John",
                "job": "Doe",
                "id": "1",
                "createdAt": "2023-05-22"
            }
        """.trimIndent()
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(responseBody)

        mockWebServer.enqueue(mockResponse)

        val result = userService.createUser(userRequest).blockingGet()

        assertEquals(expectedNewUser, result)

        val recordedRequest: RecordedRequest = mockWebServer.takeRequest()
        assertEquals("/api/users", recordedRequest.path)
        assertEquals("POST", recordedRequest.method)
        assertEquals(userRequest, recordedRequest.body.readUtf8())
    }



}
