package retrofit

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable


data class User(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String
    )
