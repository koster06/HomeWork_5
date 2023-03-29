package entities

import androidx.room.*

@Entity(
    tableName = "user_addresses",
    primaryKeys = ["user_id", "address_id"],
    foreignKeys = [
        ForeignKey(entity = UserEntity::class, parentColumns = ["id"], childColumns = ["user_id"]),
        ForeignKey(entity = AddressEntity::class, parentColumns = ["id"], childColumns = ["address_id"])
    ],
    indices = [
        Index(value = ["user_id"]),
        Index(value = ["address_id"])
    ]
)
data class UserAddressEntity(
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "address_id") val addressId: Int
)