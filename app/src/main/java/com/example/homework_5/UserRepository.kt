package com.example.homework_5

import android.app.Application
import androidx.lifecycle.LiveData
import database.MyDatabase
import entities.AddressEntity
import entities.UserAddressEntity
import entities.UserEntity
import entities.UserWithAddressEntity
import interfaces.AddressDao
import interfaces.UserAddressDao
import interfaces.UserDao
import interfaces.UserWithAddressDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(application: Application) {

    private val userDao: UserDao = MyDatabase.getInstance(application).userDao()
    private val addressDao: AddressDao = MyDatabase.getInstance(application).addressDao()
    private val userAddressDao: UserAddressDao = MyDatabase.getInstance(application).userAddressDao()
    private val userWithAddressDao: UserWithAddressDao = MyDatabase.getInstance(application).userWithAddressDao()

    fun getAllUsers(): LiveData<List<UserEntity>> {
        return userDao.getAll()
    }

    fun getUserByEmail(email: String): UserEntity? {
        return userDao.getUserByEmail(email)
    }

    suspend fun getUserById(id: Int): UserEntity? = withContext(Dispatchers.IO) {
        userDao.getUserById(id)
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

    fun addAddress(address: AddressEntity):Int {
        return addressDao.insert(address).toInt()
    }

    fun setAllAddresses(addresses: List<AddressEntity>) {
        addressDao.insertAllAddresses(addresses)
    }

    fun getAllAddresses(): LiveData<List<AddressEntity>> {
        return addressDao.getAll()
    }

    suspend fun getAddressById(id: Int): AddressEntity? = withContext(Dispatchers.IO) {
        addressDao.getAddressById(id)
    }

    fun getAllUserAddresses(): LiveData<List<UserAddressEntity>> {
        return userAddressDao.getAll()
    }

    fun addUserAddress(userAddressEntity: UserAddressEntity) {
        userAddressDao.insert(userAddressEntity)
    }

    fun getUsersAndAddresses(): List<UserWithAddressEntity> {
        return userWithAddressDao.getUsersWithAddresses()
    }

}


