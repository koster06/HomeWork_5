package database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import entities.AddressEntity
import entities.UserAddressEntity
import entities.UserEntity
import interfaces.AddressDao
import interfaces.UserAddressDao
import interfaces.UserDao
import interfaces.UserWithAddressDao

@Database(
    entities = [UserEntity::class, AddressEntity::class, UserAddressEntity::class],
    version = 1
)
abstract class MyDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun addressDao(): AddressDao

    abstract fun userAddressDao(): UserAddressDao

    abstract fun userWithAddressDao(): UserWithAddressDao

    companion object {
        @Volatile
        private var instance: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "my_database.db"
                ).build().also {
                    instance = it
                }
            }
        }
    }
}
