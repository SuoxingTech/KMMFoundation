package dev.suoxing.kmm_analytics

import com.google.firebase.crashlytics.FirebaseCrashlytics

actual object SXCrashlytics: ISXCrashlytics {

    actual override fun enable() {
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
    }

    actual override fun disable() {
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(false)
    }

    fun reportNonFatal(throwable: Throwable) {
        FirebaseCrashlytics.getInstance().recordException(throwable)
    }
}
