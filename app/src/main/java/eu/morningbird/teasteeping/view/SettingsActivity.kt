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

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.hotmail.or_dvir.easysettings.events.SwitchSettingsClickEvent
import com.hotmail.or_dvir.easysettings.pojos.EasySettings
import kotlinx.android.synthetic.main.activity_settings.*
import eu.morningbird.teasteeping.TeaSteepingApplication
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.EventBus


class SettingsActivity : AppCompatActivity() {

    private var firebaseAnalytics: FirebaseAnalytics? = null

    private lateinit var teaSteepingApplication: TeaSteepingApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(eu.morningbird.teasteeping.R.layout.activity_settings)

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        teaSteepingApplication = application as TeaSteepingApplication
        EasySettings.inflateSettingsLayout(this, settingsContainer, teaSteepingApplication.settings)


    }

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onSwitchSettingsClicked(event: SwitchSettingsClickEvent) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, event.clickedSettingsObj.key + "_" + event.clickedSettingsObj.value.toString())
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, event.clickedSettingsObj.title + " set to " + event.clickedSettingsObj.value.toString())
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "settings changed")
        firebaseAnalytics?.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
    }
}
