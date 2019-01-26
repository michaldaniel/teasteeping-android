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

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import eu.morningbird.teasteeping.R
import eu.morningbird.teasteeping.databinding.FragmentTeaPageBinding
import eu.morningbird.teasteeping.model.Tea
import eu.morningbird.teasteeping.view.model.TeaPageFragmentVM

class TeaPageFragment : Fragment() {

    private lateinit var binding: FragmentTeaPageBinding

    internal lateinit var listener: TeaPageFragmentInteraction

    private var tea: Tea? = null

    private lateinit var viewModel: TeaPageFragmentVM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tea_page, container, false)
        viewModel = TeaPageFragmentVM()
        viewModel.attach(this)
        binding.viewModel = viewModel
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

    override fun onAttach(context: Context) {
        if (context is TeaPageFragmentInteraction) {
            listener = context
        } else {
            throw ClassCastException(context.toString() + " must implement TeaPageFragmentInteraction.")
        }
        super.onAttach(context)
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
        fun newInstance(tea: Tea): TeaPageFragment {
            val fragment = TeaPageFragment()
            val args = Bundle().apply {
                putParcelable(TEA, tea)
            }
            fragment.arguments = args
            return fragment
        }
    }

    interface TeaPageFragmentInteraction {
        fun showTimeSelector(tea: Tea)
    }

}

