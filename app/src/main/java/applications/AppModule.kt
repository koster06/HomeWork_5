package applications

import dataclasses.UserService
import org.koin.dsl.module
import repository.UserRepository
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import viewmodels.UserViewModelFactory

val appModule = module {
    single { provideUserService() }
    single { UserRepository(get()) }
    factory { UserViewModelFactory(get()) }
}

private fun provideUserService(): UserService {
    return Retrofit.Builder()
        .baseUrl("https://reqres.in/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(UserService::class.java)
}

