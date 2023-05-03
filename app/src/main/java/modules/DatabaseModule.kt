package modules

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import database.MyDatabase
import interfaces.AddressDao
import interfaces.UserAddressDao
import interfaces.UserDao
import interfaces.UserWithAddressDao

@Module
class DatabaseModule(application: Application) {

    private val myDatabase = Room.databaseBuilder(application, MyDatabase::class.java, "my_database").build()

    @Provides
    fun provideUserDao(): UserDao {
        return myDatabase.userDao()
    }

    @Provides
    fun provideUserAddressDao(): UserAddressDao {
        return myDatabase.userAddressDao()
    }

    @Provides
    fun provideAddressDao(): AddressDao {
        return myDatabase.addressDao()
    }

    @Provides
    fun provideUserWithAddressDao(): UserWithAddressDao {
        return myDatabase.userWithAddressDao()
    }
}
