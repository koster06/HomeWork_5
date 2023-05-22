package com.example.homework_5

import com.example.lib.UserLib
import com.example.lib.UserResponseLib
import dataclasses.UserUnic
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import repository.UserRepository
import retrofit2.Response

class UserRepositoryTest {

    private val mockUserService: UserService = mock(UserService::class.java)

    private val userRepository = UserRepository(mockUserService)

    @Test
    fun getUsers1_returnsExpectedUserResponseLib() = runBlocking {
        // Создаем тестовые данные
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

        // Устанавливаем ожидаемое поведение для getUsers1
        `when`(mockUserService.getUsers1("users?page=1")).thenReturn(Response.success(expectedUserResponseLib))

        // Вызываем метод getUsers1 и получаем результат
        val result = userRepository.getUsers1()

        // Проверяем, что возвращаемый объект соответствует ожидаемым данным
        assertEquals(expectedUserResponseLib, result)
    }

    @Test
    fun getUser_returnsExpectedUserUnic() {
        // Создаем тестовые данные
        val expectedUserLib = UserLib(1, "test@example.com", "John", "Doe", "avatar")
        val expectedUserUnic = UserUnic(expectedUserLib)

        // Создаем mock-объект UserService
        val userService = mock(UserService::class.java)

        // Устанавливаем ожидаемое поведение для метода getUser
        `when`(userService.getUser(1)).thenReturn(Single.just(expectedUserUnic))

        // Вызываем метод getUser и получаем результат
        val result = userService.getUser(1).blockingGet()

        // Проверяем, что возвращаемый объект соответствует ожидаемым данным
        assertEquals(expectedUserUnic, result)
    }



}
