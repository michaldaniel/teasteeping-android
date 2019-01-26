package eu.morningbird.teasteeping.view.model

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
import eu.morningbird.teasteeping.model.Tea
import eu.morningbird.teasteeping.view.TeaPageFragment

class TeaPageItem(tea: Tea) {
    val item: Fragment = TeaPageFragment.newInstance(tea)
    val pageTitle: CharSequence = tea.name
}