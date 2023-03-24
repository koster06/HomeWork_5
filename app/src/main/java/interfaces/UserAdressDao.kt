package interfaces

import androidx.room.Dao
import androidx.room.Query
import adress.UserAddress
import androidx.room.Delete
import androidx.room.Insert
import user.User

@Dao
interface UserAddressDao {
    @Query("SELECT * FROM user_address")
    fun getAllUserAddresses(): List<UserAddress>

    @Query("SELECT * FROM user_address WHERE user_id = :userId")
    fun getUserAddressesForUser(userId: Long): List<UserAddress>

    @Query("SELECT * FROM user_address WHERE address_id = :addressId")
    fun getUserAddressesForAddress(addressId: Long): List<UserAddress>

    @Query("SELECT * FROM user_address WHERE address_type = :addressType")
    fun getUserAddressesByType(addressType: String): List<UserAddress>

    @Delete
    fun deleteUser(user: User)

    @Query("DELETE FROM user_address")
    fun deleteAllAddresses()

    @Insert
    fun insertUserAddress(userAddress: UserAddress)

    @Insert
    fun insertAllUsersAddresses(userAddress: List<UserAddress>)

    @Query("DELETE FROM user_address WHERE user_id = :userId")
    fun deleteByUserId(userId: Long)

}
/*
Написать select запросы для каждой таблицы (поиск всех элементов, поиск по условию,
поиск с промежутком, поиск определенного количества и т.д) Минимум 4 запроса на каждую таблицу

Написать запросы для удаления и изменения данных (для одного и для всех данных)

Написать запрос для вставки списка элементов и для вставки одного элемента.
 */