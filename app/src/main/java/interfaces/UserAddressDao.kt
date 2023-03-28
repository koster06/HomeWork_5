package interfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import entities.UserAddressEntity
import entities.UserEntity

@Dao
interface UserAddressDao {
    @Query("SELECT * FROM user_addresses")
    fun getAll(): LiveData<List<UserAddressEntity>>

    @Insert
    fun insert(userAddress: UserAddressEntity)

    @Update
    fun update(userAddress: UserAddressEntity)

    @Delete
    fun delete(userAddress: UserAddressEntity)
}