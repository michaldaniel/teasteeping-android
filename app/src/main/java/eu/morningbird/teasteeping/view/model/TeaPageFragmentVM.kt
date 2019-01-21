package eu.morningbird.teasteeping.view.model

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.hotmail.or_dvir.easysettings.pojos.EasySettings
import eu.morningbird.teasteeping.BR
import eu.morningbird.teasteeping.R
import eu.morningbird.teasteeping.TeaSteepingApplication
import eu.morningbird.common.model.Tea
import eu.morningbird.teasteeping.utils.Units
import eu.morningbird.teasteeping.view.TeaPageFragment
import java.util.concurrent.locks.ReentrantLock

class TeaPageFragmentVM : BaseObservable() {

    @Bindable
    var teaName: String? = null
        private set(value) {
            field = value
            if (isDataLoaded) notifyPropertyChanged(BR.teaName)
        }

    @Bindable
    var teaDescription: String? = null
        private set(value) {
            field = value
            if (isDataLoaded) notifyPropertyChanged(BR.teaDescription)
        }

    @Bindable
    var quantityInstructions: String? = null
        private set(value) {
            field = value
            if (isDataLoaded) notifyPropertyChanged(BR.quantityInstructions)
        }


    @Bindable
    var steepingTemperature: String? = null
        private set(value) {
            field = value
            if (isDataLoaded) notifyPropertyChanged(BR.steepingTemperature)
        }

    @Bindable
    var steepingTime: String? = null
        private set(value) {
            field = value
            if (isDataLoaded) notifyPropertyChanged(BR.steepingTime)
        }

    @Bindable
    var backgroundColor: Int = android.R.color.background_dark
        private set(value) {
            field = value
            if (isDataLoaded) notifyPropertyChanged(BR.backgroundColor)
        }

    private var tea: Tea? = null
        private set(value) {
            field = value
            teaName = value?.name
            teaDescription = value?.description
            if(EasySettings.retrieveSettingsSharedPrefs(context!!.activity).getBoolean(TeaSteepingApplication.SETTINGS_USE_IMPERIAL_UNITS, false)) {
                steepingTemperature = context!!.getString(
                    R.string.from_fahrenheit_to_fahrenheit,
                    value?.temperature?.low?.let { Units.convertCelciusToFahrenheit(it) },
                    value?.temperature?.high?.let { Units.convertCelciusToFahrenheit(it) }
                )
            }else {
                steepingTemperature = context!!.getString(
                    R.string.from_celcius_to_celcius,
                    value?.temperature?.low,
                    value?.temperature?.high
                )
            }
            steepingTime = context!!.getString(R.string.steep_for_to_minutes, value?.time?.low, value?.time?.high)
            if(EasySettings.retrieveSettingsSharedPrefs(context!!.activity).getBoolean(TeaSteepingApplication.SETTINGS_SHOW_COLORS, true)) {
                backgroundColor = value?.color ?: android.R.color.background_dark
            } else {
                backgroundColor = android.R.color.background_dark
            }
            quantityInstructions = context!!.getString(R.string.use_to_make_a_cup, value?.quantity?.quantity, value?.quantity?.unit?.str)

        }

    private var context: TeaPageFragment? = null

    private val lock = ReentrantLock()

    @Volatile
    var isDataLoaded: Boolean = false
        private set

    fun loadData(tea: Tea) {
        val runnable = Runnable {
            lock.lock()
            try {
                this.tea = tea
                isDataLoaded = true
                notifyChange()
            } finally {
                lock.unlock()
            }
        }
        Thread(runnable).start()
    }

    fun attach(context: TeaPageFragment) {
        this.context = context
    }

    @Suppress("UNUSED_PARAMETER")
    fun setTimerFloatingActionButtonOnClick(view: View) {
        this.context!!.listener.showTimeSelector(tea!!)
    }


}