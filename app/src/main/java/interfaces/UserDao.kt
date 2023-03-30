package interfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import entities.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAll(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUserById(id: Int): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email AND first_name = :first_name LIMIT 1")
    fun getUserByEmailAndPassword(email: String, password: String): UserEntity?


    @Insert
    fun insert(user: UserEntity)

    @Update
    fun update(user: UserEntity)

    @Delete
    fun delete(user: UserEntity)
}