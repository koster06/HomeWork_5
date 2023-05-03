package modules

import android.app.Application
import com.example.homework_5.UserRepository
import dagger.Module
import dagger.Provides

@Module
class UserRepositoryModule(private val application: Application) {

    @Provides
    fun provideUserRepository(): UserRepository {
        return UserRepository(application)
    }
}
