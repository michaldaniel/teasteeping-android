package eu.morningbird.teasteeping.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import eu.morningbird.teasteeping.R
import eu.morningbird.teasteeping.databinding.FragmentBottomSheetRangePickerBinding
import eu.morningbird.common.model.Tea
import eu.morningbird.teasteeping.view.model.BottomSheetRangePickerFragmentVM
import kotlinx.android.synthetic.main.fragment_bottom_sheet_range_picker.*

class BottomSheetRangePickerFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomSheetRangePickerBinding

    internal lateinit var listener: BottomSheetRangePickerFragmentInteraction

    private val onPropertyChanged = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(p0: Observable?, p1: Int) {
            updateView()
        }
    }

    private var tea: Tea? = null

    private lateinit var viewModel: BottomSheetRangePickerFragmentVM

    override fun onAttach(context: Context) {
        if (context is BottomSheetRangePickerFragmentInteraction) {
            listener = context
        } else {
            throw ClassCastException(context.toString() + " must implement BottomSheetRangePickerFragmentInteraction.")
        }
        super.onAttach(context)
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)

    @SuppressLint("MissingSuperCall")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_sheet_range_picker, container, false)
        viewModel = BottomSheetRangePickerFragmentVM()
        viewModel.attach(this)
        binding.viewModel = viewModel

        binding.timeTickSeekBar.onSeekChangeListener = viewModel.onSeekChangeListener

        viewModel.addOnPropertyChangedCallback(onPropertyChanged)
        arguments?.getParcelable<Tea>(TEA)?.let {
            tea = it
        }
        if (tea != null) {
            tea?.let { it -> viewModel.loadData(it) }
        } else {
            viewModel.notifyChange()
        }

        return binding.root
    }

    private fun updateView() {
        timeTickSeekBar.tickMarksColor(ContextCompat.getColor(context!!, viewModel.teaColor))
        timeTickSeekBar.thumbColor(ContextCompat.getColor(context!!, viewModel.teaColor))
        timeTickSeekBar.min = viewModel.minTime
        timeTickSeekBar.max = viewModel.maxTime
        timeTickSeekBar.tickCount = viewModel.tickCount
        timeTickSeekBar.setProgress(0.5f)
        //app:tsb_ticks_count="5"
    }

    companion object {
        /**
         * The fragment argument representing the page number for this
         * fragment.
         */
        private const val TEA = "TEA"

        /**
         * Returns a new instance of this fragment for the given page
         * number.
         */
        fun newInstance(tea: Tea): BottomSheetRangePickerFragment {
            val fragment = BottomSheetRangePickerFragment()
            val args = Bundle().apply {
                putParcelable(TEA, tea)
            }
            fragment.arguments = args
            return fragment
        }
    }

    interface BottomSheetRangePickerFragmentInteraction {
        fun setTimer(message: String, seconds: Int)
    }

}

