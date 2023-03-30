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

    suspend fun getUserById(id: Int): UserEntity? {
        return userDao.getUserById(id)
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

    suspend fun addAddress(address: AddressEntity):Int {
        return addressDao.insert(address).toInt()
    }

    fun setAllAddresses(addresses: List<AddressEntity>) {
        addressDao.insertAllAddresses(addresses)
    }


    fun getAllAddresses(): LiveData<List<AddressEntity>> {
        return addressDao.getAll()
    }

    suspend fun getAddressById(id: Int): LiveData<AddressEntity> {
        return addressDao.getAddressById(id)
    }

    suspend fun getAllUserAddresses(): LiveData<List<UserAddressEntity>> {
        return userAddressDao.getAll()
    }

    suspend fun addUserAddress(userAddressEntity: UserAddressEntity) {
        userAddressDao.insert(userAddressEntity)
    }

}


