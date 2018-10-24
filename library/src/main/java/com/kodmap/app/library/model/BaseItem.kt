package com.kodmap.app.library.model

import android.os.Parcel
import android.os.Parcelable

open class BaseItem(
        var drawableId: Int? = null,
        var imageUrl: String? = null,
        var isSelected: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readByte() != 0.toByte()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(drawableId)
        parcel.writeString(imageUrl)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BaseItem> {
        override fun createFromParcel(parcel: Parcel): BaseItem {
            return BaseItem(parcel)
        }

        override fun newArray(size: Int): Array<BaseItem?> {
            return arrayOfNulls(size)
        }
    }

}