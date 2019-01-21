package eu.morningbird.teasteeping.view.model

import androidx.fragment.app.Fragment
import eu.morningbird.common.model.Tea
import eu.morningbird.teasteeping.view.TeaPageFragment

class TeaPageItem(tea: Tea) {
    val item: Fragment = TeaPageFragment.newInstance(tea)
    val pageTitle: CharSequence = tea.name
}