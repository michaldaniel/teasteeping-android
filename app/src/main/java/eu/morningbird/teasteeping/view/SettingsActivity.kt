package eu.morningbird.teasteeping.view

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
