package modules

import com.example.homework_5.MainActivity
import dagger.Component

@Component(
    modules = [
        DatabaseModule::class,
        ApiServiceModule::class,
        ViewModelModule::class,
        ApplicationModule::class,
        UserRepositoryModule::class
    ])
interface MyComponent {
    fun inject(activity: MainActivity)
}
