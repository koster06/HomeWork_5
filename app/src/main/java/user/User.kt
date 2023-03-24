package user

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.VersionedParcelize

@VersionedParcelize
@Entity (
    tableName = "users"
)
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name:String,
    val secName: String,
    val phone: String,
    val age: String,
    val birthday:String
    )