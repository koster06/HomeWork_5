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
    @PrimaryKey(autoGenerate = true) val id: Int,
    var email: String,
    var first_name: String,
    var last_name: String,
    var avatar: String
)
