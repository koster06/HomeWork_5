package interfaces

import androidx.room.*
import user.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAllUsers(): List<User>

//    @Query("SELECT * FROM users WHERE email = :email")
//    fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUserById(id: Long): User?

    @Insert
    fun insertUser(user: User)

    @Query("SELECT * FROM users")
    fun getAll(): List<User>

    @Insert
    fun insertAll(vararg users: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)
}
