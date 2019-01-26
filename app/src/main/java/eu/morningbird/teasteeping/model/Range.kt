package eu.morningbird.teasteeping.model

/*
 *  This file is part of "Tea steeping" android application.
 *
 *  "Tea steeping" is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  "Tea steeping" is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with "Tea steeping". If not, see <http://www.gnu.org/licenses/>.
*/

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