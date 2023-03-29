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

    fun addUser(user: UserEntity) {
        userDao.insert(user)
    }

    fun updateUser(user: UserEntity) {
        userDao.update(user)
    }

    fun deleteUser(user: UserEntity){
        userDao.delete(user)
    }

    // Методы для работы с таблицей адресов

    fun getAllAddresses(): LiveData<List<AddressEntity>> {
        return addressDao.getAll()
    }

    fun setAllAddresses(addresses: List<AddressEntity>) {
        addresses.forEach {
            addressDao.insert(it)
        }
    }

    fun getAllUserAddresses(): LiveData<List<UserAddressEntity>> {
        return userAddressDao.getAll()
    }

    fun addUserAddress(userAddress: UserAddressEntity) {
        userAddressDao.insert(userAddress)
    }

    fun setAllUserAddresses(userAddresses: List<UserAddressEntity>) {
        userAddresses.forEach {
            userAddressDao.insert(it)
        }
    }

    fun getUserById(userId: Int): LiveData<UserEntity> {
        return userDao.getUserById(userId)
    }

}


