package eu.morningbird.common.model

import android.os.Parcel
import android.os.Parcelable

data class Range(
    val low: Int,
    val high: Int
) : Parcelable {

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(low)
        parcel.writeInt(high)
    }

    override fun describeContents(): Int {
        return 0
    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt()
    )

    companion object CREATOR : Parcelable.Creator<Range> {
        override fun createFromParcel(parcel: Parcel): Range {
            return Range(parcel)
        }

        override fun newArray(size: Int): Array<Range?> {
            return arrayOfNulls(size)
        }
    }
}