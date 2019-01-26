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

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.Observable
import com.google.firebase.analytics.FirebaseAnalytics
import com.kobakei.ratethisapp.RateThisApp
import kotlinx.android.synthetic.main.activity_main.*
import eu.morningbird.teasteeping.R
import eu.morningbird.teasteeping.databinding.ActivityMainBinding
import eu.morningbird.teasteeping.model.Tea
import eu.morningbird.teasteeping.view.model.MainActivityVM

class MainActivity : AppCompatActivity(), TeaPageFragment.TeaPageFragmentInteraction,
    BottomSheetRangePickerFragment.BottomSheetRangePickerFragmentInteraction {

    private lateinit var binding: ActivityMainBinding

    private var firebaseAnalytics: FirebaseAnalytics? = null

    private val onPropertyChanged = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(p0: Observable?, p1: Int) {
            runOnUiThread {
                updateView()
            }
        }
    }

    lateinit var view: View

    private lateinit var viewModel: MainActivityVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        view = binding.root
        setContentView(binding.root)
        initView()

        RateThisApp.onCreate(this)
        RateThisApp.showRateDialogIfNeeded(this)
    }

    override fun onResume() {
        viewModel.addOnPropertyChangedCallback(onPropertyChanged)
        super.onResume()
    }

    override fun onPause() {
        viewModel.removeOnPropertyChangedCallback(onPropertyChanged)
        super.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            return true
        }
        if (id == R.id.action_about) {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun showTimeSelector(tea: Tea) {
        viewModel.showTimeSelector(tea)
    }

    override fun setTimer(message: String, seconds: Int) {
        viewModel.setTimer(message, seconds)
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "timer_set")
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "timer set")
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "timer interaction")
        firebaseAnalytics?.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
    }

    private fun initView() {
        viewModel = MainActivityVM()
        viewModel.attach(this)
        binding.viewModel = viewModel

        viewModel.addOnPropertyChangedCallback(onPropertyChanged)
        if (!viewModel.isDataLoaded) {
            viewModel.loadData()
        } else {
            viewModel.notifyChange()
        }

        setSupportActionBar(toolbar)
        teaTabLayout.setupWithViewPager(teaViewPager)

        modifyLooks()
    }

    private fun modifyLooks() {
    }

    private fun updateView() {

    }


}
