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