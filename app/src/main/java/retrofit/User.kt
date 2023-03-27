package retrofit

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable


data class User(
    val id: Int,
    val email: String,
    val first_name: String,
    val last_name: String,
    val avatar: String
    )
