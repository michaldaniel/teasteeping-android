package eu.morningbird.teasteeping.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import eu.morningbird.teasteeping.view.model.TeaPageItem


class TeaPageAdapter(private val fm: FragmentManager, private val items: List<TeaPageItem>) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Fragment = items[position].item

    override fun getPageTitle(position: Int): CharSequence = items[position].pageTitle

}

