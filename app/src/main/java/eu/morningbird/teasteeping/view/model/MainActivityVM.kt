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

import android.content.Intent
import android.provider.AlarmClock
import androidx.core.content.ContextCompat
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.android.material.snackbar.Snackbar
import com.hotmail.or_dvir.easysettings.pojos.EasySettings
import eu.morningbird.teasteeping.BR
import eu.morningbird.teasteeping.R
import eu.morningbird.teasteeping.TeaSteepingApplication
import eu.morningbird.teasteeping.model.Quantity
import eu.morningbird.teasteeping.model.Range
import eu.morningbird.teasteeping.model.Tea
import eu.morningbird.teasteeping.model.TeaUnit
import eu.morningbird.teasteeping.view.BottomSheetRangePickerFragment
import eu.morningbird.teasteeping.view.MainActivity
import eu.morningbird.teasteeping.view.TeaPageAdapter
import eu.morningbird.teasteeping.view.TeaPageFragment
import java.util.concurrent.locks.ReentrantLock

class MainActivityVM : BaseObservable(), BottomSheetRangePickerFragment.BottomSheetRangePickerFragmentInteraction,
    TeaPageFragment.TeaPageFragmentInteraction {

    private var context: MainActivity? = null

    @Volatile
    var isDataLoaded: Boolean = false
        private set

    private val lock = ReentrantLock()

    private var sheet: BottomSheetRangePickerFragment? = null

    @Bindable
    var teaViewPagerAdapter: TeaPageAdapter? = null
        private set(value) {
            field = value
            if (isDataLoaded) notifyPropertyChanged(BR.teaViewPagerAdapter)
        }

    private val teas: ArrayList<Tea> = ArrayList()

    override fun showTimeSelector(tea: Tea) {
        sheet = BottomSheetRangePickerFragment.newInstance(tea)
        sheet?.show(context!!.supportFragmentManager, "BottomSheetRangePickerFragment")
    }

    override fun setTimer(message: String, seconds: Int) {
        val intent = Intent(AlarmClock.ACTION_SET_TIMER).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, message)
            putExtra(AlarmClock.EXTRA_LENGTH, seconds)
            putExtra(AlarmClock.EXTRA_SKIP_UI, EasySettings.retrieveSettingsSharedPrefs(context).getBoolean(TeaSteepingApplication.SETTINGS_SKIP_TIMER_UI, false))
        }
        if (intent.resolveActivity(context!!.packageManager) != null) {
            context!!.startActivity(intent)
        }
        sheet?.dismiss()
        if(EasySettings.retrieveSettingsSharedPrefs(context).getBoolean(TeaSteepingApplication.SETTINGS_SKIP_TIMER_UI, false)){
            Snackbar.make(context!!.view, R.string.stopwatch_started, Snackbar.LENGTH_LONG)
                .setAction(R.string.ok) {  }
                .setActionTextColor(ContextCompat.getColor(context!!, R.color.colorAccent))
                .setDuration(6000).show()        }
    }

    fun attach(context: MainActivity) {
        this.context = context
    }

    fun loadData() {
        val runnable = Runnable {
            lock.lock()
            try {
                teas.add(
                    Tea(
                        context!!.getString(R.string.tea_white),
                        context!!.getString(R.string.tea_description_white),
                        R.color.colorTeaWhite,
                        Range(75, 85),
                        Range(2, 5),
                        Quantity(1, TeaUnit.TBSP)
                    )
                )
                teas.add(
                    Tea(
                        context!!.getString(R.string.tea_green),
                        context!!.getString(R.string.tea_description_green),
                        R.color.colorTeaGreen,
                        Range(60, 85),
                        Range(1, 3),
                        Quantity(1, TeaUnit.TSP)
                    )
                )
                teas.add(
                    Tea(
                        context!!.getString(R.string.tea_black),
                        context!!.getString(R.string.tea_description_black),
                        R.color.colorTeaBlack,
                        Range(95, 100),
                        Range(2, 4),
                        Quantity(1, TeaUnit.TSP)
                    )
                )
                teas.add(
                    Tea(
                        context!!.getString(R.string.tea_oolong),
                        context!!.getString(R.string.tea_description_oolong),
                        R.color.colorTeaOolong,
                        Range(80, 90),
                        Range(3, 5),
                        Quantity(1, TeaUnit.TSP)
                    )
                )
                teas.add(
                    Tea(
                        context!!.getString(R.string.tea_raw_puerh),
                        context!!.getString(R.string.tea_description_raw_puerh),
                        R.color.colorTeaRawPuerh,
                        Range(90, 95),
                        Range(3, 5),
                        Quantity(1, TeaUnit.TBSP)
                    )
                )
                teas.add(
                    Tea(
                        context!!.getString(R.string.tea_half_full_baked_puerh),
                        context!!.getString(R.string.tea_description_half_full_baked_puerh),
                        R.color.colorTeaHalfFullBakedPuerh,
                        Range(95, 100),
                        Range(3, 5),
                        Quantity(1, TeaUnit.TBSP)
                    )
                )
                teas.add(
                    Tea(
                        context!!.getString(R.string.tea_jasmine),
                        context!!.getString(R.string.tea_description_jasmine),
                        R.color.colorTeaJasmine,
                        Range(75, 85),
                        Range(1, 3),
                        Quantity(1, TeaUnit.TSP)
                    )
                )
                teas.add(
                    Tea(
                        context!!.getString(R.string.tea_yellow),
                        context!!.getString(R.string.tea_description_yellow),
                        R.color.colorTeaYellow,
                        Range(75, 85),
                        Range(2, 4),
                        Quantity(1, TeaUnit.TSP)
                    )
                )
                teas.add(
                    Tea(
                        context!!.getString(R.string.tea_rooibos),
                        context!!.getString(R.string.tea_description_rooibos),
                        R.color.colorTeaRooibos,
                        Range(95, 100),
                        Range(3, 6),
                        Quantity(1, TeaUnit.TSP)
                    )
                )
                teas.add(
                    Tea(
                        context!!.getString(R.string.tea_herbal),
                        context!!.getString(R.string.tea_description_herbal),
                        R.color.colorTeaHerbal,
                        Range(95, 100),
                        Range(5, 10),
                        Quantity(1, TeaUnit.TSP)
                    )
                )
                teas.add(
                    Tea(
                        context!!.getString(R.string.tea_darjeeling),
                        context!!.getString(R.string.tea_description_darjeeling),
                        R.color.colorTeaDarjeeling,
                        Range(85, 100),
                        Range(2, 4),
                        Quantity(1, TeaUnit.TSP)
                    )
                )
                teas.add(
                    Tea(
                        context!!.getString(R.string.tea_matcha),
                        context!!.getString(R.string.tea_description_matcha),
                        R.color.colorTeaMatcha,
                        Range(75, 95),
                        Range(1, 3),
                        Quantity(1, TeaUnit.TBSP)
                    )
                )
                teas.add(
                    Tea(
                        context!!.getString(R.string.tea_mate),
                        context!!.getString(R.string.tea_description_mate),
                        R.color.colorTeaMate,
                        Range(65, 90),
                        Range(3, 5),
                        Quantity(2, TeaUnit.TSP)
                    )
                )

                teaViewPagerAdapter = context?.supportFragmentManager?.let { manager ->
                    TeaPageAdapter(
                        manager,
                        teas.map { TeaPageItem(it) })
                }
                isDataLoaded = true
                notifyChange()
            } finally {
                lock.unlock()
            }
        }
        Thread(runnable).start()
    }

}