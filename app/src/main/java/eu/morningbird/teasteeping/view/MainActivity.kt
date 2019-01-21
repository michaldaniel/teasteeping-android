package eu.morningbird.teasteeping.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.Observable
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_main.*
import eu.morningbird.teasteeping.R
import eu.morningbird.teasteeping.databinding.ActivityMainBinding
import eu.morningbird.common.model.Tea
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
