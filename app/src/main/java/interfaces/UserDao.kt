package interfaces

import androidx.room.*
import entities.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAll(): List<UserEntity>

    @Insert
    fun insert(user: UserEntity)

    @Update
    fun update(user: UserEntity)

    @Delete
    fun delete(user: UserEntity)
}