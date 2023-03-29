package entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "addresses")
data class AddressEntity(
    @PrimaryKey val id: Int,
    val street: String,
    val city: String,
    val state: String,
    val zip: String
)