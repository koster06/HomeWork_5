package com.example.homework_5

import adress.UserAddress
import androidx.room.Database
import androidx.room.RoomDatabase
import interfaces.UserDao
import interfaces.UserAddressDao
import user.User

@Database(entities = [User::class, UserAddress::class], version = 1)
abstract class AppDB : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun addressDao(): UserAddressDao
}