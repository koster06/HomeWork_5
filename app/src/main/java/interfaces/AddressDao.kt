package interfaces

import androidx.room.*
import entities.AddressEntity

@Dao
interface AddressDao {
    @Query("SELECT * FROM addresses")
    fun getAll(): List<AddressEntity>

    @Insert
    fun insert(address: AddressEntity)

    @Update
    fun update(address: AddressEntity)

    @Delete
    fun delete(address: AddressEntity)
}