package entities

import androidx.room.*

@Entity(tableName = "user_addresses",
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
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "address_id") val addressId: Int
)