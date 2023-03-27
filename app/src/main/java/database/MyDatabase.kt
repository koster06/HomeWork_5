package database

import androidx.room.Database
import androidx.room.RoomDatabase
import entities.AddressEntity
import entities.UserAddressEntity
import entities.UserEntity
import interfaces.AddressDao
import interfaces.UserAddressDao
import interfaces.UserDao

@Database(
    entities = [UserEntity::class, AddressEntity::class, UserAddressEntity::class],
    version = 1
)
abstract class MyDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun addressDao(): AddressDao

    abstract fun userAddressDao(): UserAddressDao
}
