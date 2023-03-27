package interfaces

import androidx.room.*
import entities.UserAddressEntity

@Dao
interface UserAddressDao {
    @Query("SELECT * FROM user_addresses")
    fun getAll(): List<UserAddressEntity>

    @Insert
    fun insert(userAddress: UserAddressEntity)

    @Update
    fun update(userAddress: UserAddressEntity)

    @Delete
    fun delete(userAddress: UserAddressEntity)
}