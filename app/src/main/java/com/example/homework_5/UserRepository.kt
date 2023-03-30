package com.example.homework_5

import android.app.Application
import androidx.lifecycle.LiveData
import database.MyDatabase
import entities.AddressEntity
import entities.UserAddressEntity
import entities.UserEntity
import interfaces.AddressDao
import interfaces.UserAddressDao
import interfaces.UserDao

class UserRepository(application: Application) {

    private val userDao: UserDao = MyDatabase.getInstance(application).userDao()
    private val addressDao: AddressDao = MyDatabase.getInstance(application).addressDao()
    private val userAddressDao: UserAddressDao = MyDatabase.getInstance(application).userAddressDao()

    // Методы для работы с таблицей пользователей

    fun getAllUsers(): LiveData<List<UserEntity>> {
        return userDao.getAll()
    }

    suspend fun getUserByEmail(email: String): UserEntity? {
        return userDao.getUserByEmail(email)
    }

    fun addUser(user: UserEntity) {
        userDao.insert(user)
    }

    fun updateUser(user: UserEntity) {
        userDao.update(user)
    }

    fun deleteUser(user: UserEntity){
        userDao.delete(user)
    }

    fun addAddress(address: AddressEntity) {
        addressDao.insert(address)
    }

    fun setAllAddresses(addresses: List<AddressEntity>) {
        addressDao.insertAllAddresses(addresses)
    }


    fun getAllAddresses(): LiveData<List<AddressEntity>> {
        return addressDao.getAll()
    }



    fun getAllUserAddresses(): LiveData<List<UserAddressEntity>> {
        return userAddressDao.getAll()
    }

}


