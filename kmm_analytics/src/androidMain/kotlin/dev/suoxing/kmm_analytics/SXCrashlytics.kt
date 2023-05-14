package dev.suoxing.kmm_analytics

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase

actual object SXCrashlytics: ISXCrashlytics {

    override fun enable() {
        Firebase.crashlytics.setCrashlyticsCollectionEnabled(true)
    }

    override fun disable() {
        Firebase.crashlytics.setCrashlyticsCollectionEnabled(false)
    }
}