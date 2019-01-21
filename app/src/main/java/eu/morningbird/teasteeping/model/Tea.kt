package eu.morningbird.common.model

import android.os.Parcel
import android.os.Parcelable

data class Tea(
    val name: String,
    val description: String,
    val color: Int,
    val temperature: Range,
    val time: Range,
    val quantity: Quantity
) : Parcelable {

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeInt(color)
        parcel.writeParcelable(temperature, flags)
        parcel.writeParcelable(time, flags)
        parcel.writeParcelable(quantity, flags)

    }

    override fun describeContents(): Int {
        return 0
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readParcelable(Range::class.java.classLoader),
        parcel.readParcelable(Range::class.java.classLoader),
        parcel.readParcelable(Quantity::class.java.classLoader)
    )

    companion object CREATOR : Parcelable.Creator<Tea> {
        override fun createFromParcel(parcel: Parcel): Tea {
            return Tea(parcel)
        }

        override fun newArray(size: Int): Array<Tea?> {
            return arrayOfNulls(size)
        }
    }
}