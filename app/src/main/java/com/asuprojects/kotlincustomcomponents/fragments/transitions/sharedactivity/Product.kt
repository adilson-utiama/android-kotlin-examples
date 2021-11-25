package com.asuprojects.kotlincustomcomponents.fragments.transitions.sharedactivity

import android.os.Parcel
import android.os.Parcelable

class Product(
    var resourceId: Int,
    var title: String,
    var description: String,
    var details: String,
    var price: Double
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString().toString(),
        source.readString().toString(),
        source.readString().toString(),
        source.readDouble()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(resourceId)
        writeString(title)
        writeString(description)
        writeString(details)
        writeDouble(price)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Product> = object : Parcelable.Creator<Product> {
            override fun createFromParcel(source: Parcel): Product = Product(source)
            override fun newArray(size: Int): Array<Product?> = arrayOfNulls(size)
        }
    }
}