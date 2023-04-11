package entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    indices = [
        Index("email", unique = true)
    ]
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "user_id") val id: Int,
    val first_name: String,
    val last_name: String,
    val email: String,
    val avatar: String
)
