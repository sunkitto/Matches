package com.sunkitto.matches

import android.app.Application
import com.onesignal.OneSignal
import com.onesignal.debug.LogLevel
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val ONESIGNAL_APP_ID = "07bfd813-bf1f-46d8-8526-7d8dfb40ccff"

@HiltAndroidApp
class MatchesApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG_MODE) {
            OneSignal.Debug.logLevel = LogLevel.VERBOSE
        }

        OneSignal.initWithContext(this, ONESIGNAL_APP_ID)

        CoroutineScope(Dispatchers.IO).launch {
            OneSignal.Notifications.requestPermission(true)
        }
    }
}