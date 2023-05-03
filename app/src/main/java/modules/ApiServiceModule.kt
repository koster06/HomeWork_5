package modules

import Constants.Constants.REQRES
import dagger.Module
import dagger.Provides
import retrofit.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiServiceModule {
    @Provides
    fun provideMyApiService(): UserService {
        return Retrofit.Builder()
            .baseUrl(REQRES)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserService::class.java)
    }
}