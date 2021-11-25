package com.asuprojects.kotlincustomcomponents.fragments.transitions.sharedfragment

import android.os.Parcel
import android.os.Parcelable

class Weapon(
    var resourdId: Int,
    var model: String
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString().toString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(resourdId)
        writeString(model)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Weapon> = object : Parcelable.Creator<Weapon> {
            override fun createFromParcel(source: Parcel): Weapon = Weapon(source)
            override fun newArray(size: Int): Array<Weapon?> = arrayOfNulls(size)
        }
    }
}