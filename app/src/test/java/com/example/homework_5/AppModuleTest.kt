package com.example.homework_5

import applications.provideUserService
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.Test

class AppModuleTest {

    @Test
    fun provideUserService_success() {
        val userService = provideUserService()

        assertNotNull(userService)
    }
    //Проверка успешного создания экземпляра UserService

    @Test
    fun provideUserService_baseUrl() {
        val userService = provideUserService()

        val expectedBaseUrl = "https://reqres.in/api/"
        val actualBaseUrl = runBlocking {
            userService.getUsers1("").raw().request().url().toString()
        }

        assertEquals(expectedBaseUrl, actualBaseUrl)
    }
    //Проверка базового URL

}