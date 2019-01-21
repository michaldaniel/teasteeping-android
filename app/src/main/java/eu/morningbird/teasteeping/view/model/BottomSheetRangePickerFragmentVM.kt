package eu.morningbird.teasteeping.view.model

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.warkiz.tickseekbar.OnSeekChangeListener
import com.warkiz.tickseekbar.SeekParams
import com.warkiz.tickseekbar.TickSeekBar
import eu.morningbird.teasteeping.BR
import eu.morningbird.teasteeping.R
import eu.morningbird.common.model.Tea
import eu.morningbird.teasteeping.view.BottomSheetRangePickerFragment
import java.util.concurrent.locks.ReentrantLock

class BottomSheetRangePickerFragmentVM : BaseObservable() {


    private var context: BottomSheetRangePickerFragment? = null

    @Volatile
    var isDataLoaded: Boolean = false
        private set

    private val lock = ReentrantLock()

    @Bindable
    var maxTime: Float = 5f
        private set(value) {
            field = value
            if (isDataLoaded) notifyPropertyChanged(BR.maxTime)
        }

    @Bindable
    var minTime: Float = 1f
        private set(value) {
            field = value
            if (isDataLoaded) notifyPropertyChanged(BR.minTime)
        }

    var minutesSelected: Int = 2
        private set

    val onSeekChangeListener: OnSeekChangeListener = object : OnSeekChangeListener {
        override fun onSeeking(seekParams: SeekParams?) {

        }

        override fun onStartTrackingTouch(seekBar: TickSeekBar?) {}

        override fun onStopTrackingTouch(seekBar: TickSeekBar?) {
            minutesSelected = seekBar?.progress ?: 2
        }

    }

    private var tea: Tea? = null
        private set(value) {
            field = value
            maxTime = (value?.time?.high ?: 5).toFloat()
            minTime = (value?.time?.low ?: 1).toFloat()
            minutesSelected = (value?.time?.low ?: 1)
            tickCount = (maxTime - minTime + 1f).toInt()
            teaColor = value?.color ?: android.R.color.background_dark
        }

    @Bindable
    var teaColor: Int = android.R.color.background_dark
        private set(value) {
            field = value
            if (isDataLoaded) notifyPropertyChanged(BR.teaColor)
        }

    @Bindable
    var tickCount: Int = 2
        private set(value) {
            field = value
            if (isDataLoaded) notifyPropertyChanged(BR.tickCount)
        }

    fun attach(context: BottomSheetRangePickerFragment) {
        this.context = context
    }

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

    @Suppress("UNUSED_PARAMETER")
    fun setTimerFloatingActionButtonOnClick(view: View) {
        this.context!!.listener.setTimer(
            context!!.getString(R.string.tea_steeping, tea?.name), minutesSelected * 60
        )
    }


}