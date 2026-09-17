package dev.suoxing.kmm_analytics

import android.content.Context

/**
 * Android Crashlytics adapter with explicit SDK initialization.
 */
interface CrashlyticsAdapter : ICrashlyticsAdapter {

    fun initApp(context: Context)
}

/**
 * Initializes the registered Android Crashlytics adapter with [context].
 */
fun SXCrashlytics.initApp(context: Context) {
    val adapter = registeredAdapter()
    check(adapter is CrashlyticsAdapter) {
        "The registered Crashlytics adapter must implement CrashlyticsAdapter"
    }
    adapter.initApp(context)
}
