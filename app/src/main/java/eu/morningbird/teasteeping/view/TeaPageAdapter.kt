package eu.morningbird.teasteeping.view

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

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import eu.morningbird.teasteeping.view.model.TeaPageItem


class TeaPageAdapter(private val fm: FragmentManager, private val items: List<TeaPageItem>) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Fragment = items[position].item

    override fun getPageTitle(position: Int): CharSequence = items[position].pageTitle

}

