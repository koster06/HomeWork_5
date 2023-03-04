package com.example.homework_5

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

data class UserNext(val id:Int, val name:String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString()
    ) {
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(name)
    }

    companion object CREATOR : Parcelable.Creator<UserNext> {
        override fun createFromParcel(parcel: Parcel): UserNext {
            return UserNext(parcel)
        }

        override fun newArray(size: Int): Array<UserNext?> {
            return arrayOfNulls(size)
        }
    }
}
