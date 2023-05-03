package hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dataclasses.UserService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RetrofitService @Inject constructor(private val userService: UserService) {
    fun getUsers(page:Int) = userService.getUsers(page)
}
