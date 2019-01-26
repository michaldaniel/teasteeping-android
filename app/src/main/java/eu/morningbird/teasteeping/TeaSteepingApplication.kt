package eu.morningbird.teasteeping

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

import android.app.Application
import com.hotmail.or_dvir.easysettings.pojos.*
import kotlin.collections.ArrayList
import com.kobakei.ratethisapp.RateThisApp

class TeaSteepingApplication : Application() {

    //TODO: Move tea list creation out of MainActivityVM
    //TODO: Move utils and model packages to separate module
    //TODO: Don't use parcelable in model
    //TODO: I don't like settings initialization here but IDK what to do

    var settings: ArrayList<SettingsObject<Any>>? = ArrayList()
    override fun onCreate() {
        super.onCreate()

        settings = EasySettings.createSettingsArray(
            SwitchSettingsObject.Builder(
                SETTINGS_USE_IMPERIAL_UNITS,
                getString(R.string.settings_units),
                false
            ).setSummary(getString(R.string.settings_description_units)).build(),
            SwitchSettingsObject.Builder(
                SETTINGS_SKIP_TIMER_UI,
                getString(R.string.settings_timer),
                false
            ).setSummary(getString(R.string.settings_timer_description)).build(),
            SwitchSettingsObject.Builder(
                SETTINGS_SHOW_COLORS,
                getString(R.string.settings_ui),
                true
            ).setSummary(getString(R.string.settings_ui_description)).build()
        )
        EasySettings.initializeSettings(this, settings)

        val config = RateThisApp.Config()
        config.setTitle(R.string.rate_dialog_title)
        config.setMessage(R.string.rate_dialog_message)
        config.setYesButtonText(R.string.rate_dialog_rate)
        config.setNoButtonText(R.string.rate_dialog_thanks)
        config.setCancelButtonText(R.string.rate_dialog_cancel)
        RateThisApp.init(config)


    }

    companion object Settings {
        const val SETTINGS_SHOW_COLORS = "settings_show_colors"
        const val SETTINGS_SKIP_TIMER_UI = "settings_skip_timer_ui"
        const val SETTINGS_USE_IMPERIAL_UNITS = "settings_use_imperial_units"
    }

}