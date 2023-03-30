package interfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import entities.UserAddressEntity
import entities.UserEntity

@Dao
interface UserAddressDao {
    @Query("SELECT * FROM user_addresses")
    fun getAll(): LiveData<List<UserAddressEntity>>

    @Query("SELECT users.*, addresses.* FROM users JOIN user_addresses ON users.id = user_addresses.user_id JOIN addresses ON addresses.id = user_addresses.address_id")
    fun getUsersWithAddresses(): LiveData<List<UserAddressEntity>>

    @Query("SELECT users.* FROM users JOIN user_addresses ON users.id = user_addresses.user_id")
    fun getUsersAddressesIfExist(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM users WHERE id NOT IN (SELECT user_id FROM user_addresses)")
    fun getUsersWithoutAddresses(): LiveData<List<UserEntity>>


    @Insert
    fun insert(userAddress: UserAddressEntity)

    @Update
    fun update(userAddress: UserAddressEntity)

    @Delete
    fun delete(userAddress: UserAddressEntity)
}