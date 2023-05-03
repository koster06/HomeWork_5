package entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "addresses")
data class AddressEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "address_id") val id: Int,
    val street: String,
    val city: String,
    val state: String,
    val zip: String
)