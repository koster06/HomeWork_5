package interfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import entities.UserAddressEntity
import entities.UserEntity

@Dao
interface UserAddressDao {
    @Query("SELECT * FROM user_addresses")
    fun getAll(): LiveData<List<UserAddressEntity>>

    @Query("SELECT user_addresses.id, user_addresses.user_id, user_addresses.address_id, users.first_name, users.last_name, users.email, users.avatar, addresses.street, addresses.city, addresses.state, addresses.zip FROM user_addresses JOIN users ON user_addresses.user_id = users.user_id JOIN addresses ON user_addresses.address_id = addresses.address_id")
    fun getUsersWithAddresses(): LiveData<List<UserAddressEntity>>

//    @Query("SELECT * FROM users")
//    fun getUsersWithAddresses(): LiveData<List<UserAddressEntity>>

    @Query("SELECT users.* FROM users JOIN user_addresses ON users.user_id = user_addresses.user_id")
    fun getUsersAddressesIfExist(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM users WHERE user_id NOT IN (SELECT user_id FROM user_addresses)")
    fun getUsersWithoutAddresses(): LiveData<List<UserEntity>>


    @Insert
    fun insert(userAddress: UserAddressEntity)

    @Update
    fun update(userAddress: UserAddressEntity)

    @Delete
    fun delete(userAddress: UserAddressEntity)
}