package dev.suoxing.kmm_analytics

/**
 * iOS Crashlytics adapter with explicit SDK initialization.
 */
interface CrashlyticsAdapter : ICrashlyticsAdapter {

    fun initApp()
}

/**
 * Initializes the registered iOS Crashlytics adapter.
 */
fun SXCrashlytics.initApp() {
    val adapter = registeredAdapter()
    check(adapter is CrashlyticsAdapter) {
        "The registered Crashlytics adapter must implement CrashlyticsAdapter"
    }
    adapter.initApp()
}
