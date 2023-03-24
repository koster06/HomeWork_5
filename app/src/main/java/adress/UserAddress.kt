package adress

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import user.User

@Entity(tableName = "user_address",
    primaryKeys = ["user_id", "address_id"],
    foreignKeys = [
        ForeignKey(entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE)
    ])
data class UserAddress (
    @ColumnInfo(name = "user_id") val userId: Long,
    @ColumnInfo(name = "address_id") val addressId: Long,
    @ColumnInfo(name = "address_type") val addressType: String
)

