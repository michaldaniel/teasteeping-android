package eu.morningbird.common.model

import android.os.Parcel
import android.os.Parcelable

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
data class Quantity (
    val quantity: Int,
    val unit: TeaUnit
) : Parcelable {

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(quantity)
        parcel.writeParcelable(unit, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readParcelable(TeaUnit::class.java.classLoader)
    )

    companion object CREATOR : Parcelable.Creator<Quantity> {
        override fun createFromParcel(parcel: Parcel): Quantity {
            return Quantity(parcel)
        }

        override fun newArray(size: Int): Array<Quantity?> {
            return arrayOfNulls(size)
        }
    }


}