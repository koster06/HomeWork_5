package entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UserWithAddressEntity(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "address_id",
        associateBy = Junction(UserAddressEntity::class)
    )
    val addresses: List<AddressEntity>
)
