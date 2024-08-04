package com.example.myapplication.Model

import android.os.Parcel
import android.os.Parcelable

data class EditModel(
    var imagePath: String,
    var seekbar: Float,
    var sizeImage: String,
    var typeImage: String,
    var width: Int,
    var height: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readFloat(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imagePath)
        parcel.writeFloat(seekbar)
        parcel.writeString(sizeImage)
        parcel.writeString(typeImage)
        parcel.writeInt(width)
        parcel.writeInt(height)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EditModel> {
        override fun createFromParcel(parcel: Parcel): EditModel {
            return EditModel(parcel)
        }

        override fun newArray(size: Int): Array<EditModel?> {
            return arrayOfNulls(size)
        }
    }
}
