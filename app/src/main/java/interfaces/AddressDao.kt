package interfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import entities.AddressEntity

@Dao
interface AddressDao {
    @Query("SELECT * FROM addresses")
    fun getAll(): LiveData<List<AddressEntity>>

    @Insert
    fun insert(address: AddressEntity):Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAddresses(addresses: List<AddressEntity>)

    @Query("SELECT * FROM addresses WHERE id = :id")
    fun getAddressById(id: Int): LiveData<AddressEntity>

    @Update
    fun update(address: AddressEntity)

    @Delete
    fun delete(address: AddressEntity)
}