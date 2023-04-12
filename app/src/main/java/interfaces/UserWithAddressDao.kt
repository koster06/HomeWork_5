package interfaces

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import entities.UserWithAddressEntity

@Dao
interface UserWithAddressDao {
//    @Query("SELECT * FROM users INNER JOIN addresses ON users.address_id = addresses.id WHERE users.first_name LIKE :name")
//    fun getUsersWithAddresses(name: String): UserWithAddressEntity

    @Transaction
    @Query("SELECT * FROM users")
    fun getUsersWithAddresses(): List<UserWithAddressEntity>



}