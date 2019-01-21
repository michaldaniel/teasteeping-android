package eu.morningbird.common.model

import android.os.Parcel
import android.os.Parcelable

enum class TeaUnit(val str: String) : Parcelable {
    TSP("tsp"), TBSP("tbsp");

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(ordinal)
    }

    override fun describeContents(): Int {
        return 0
    }

    constructor(parcel: Parcel) : this(TeaUnit.values()[parcel.readInt()].str)

    companion object CREATOR : Parcelable.Creator<TeaUnit> {
        override fun createFromParcel(parcel: Parcel): TeaUnit {
            return TeaUnit.values()[parcel.readInt()]
        }

        override fun newArray(size: Int): Array<TeaUnit?> {
            return newArray(size)
        }
    }
}