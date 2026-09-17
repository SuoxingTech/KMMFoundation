package dev.suoxing.kmm_analytics

/**
 * iOS analytics adapter with explicit SDK initialization.
 */
interface AnalyticsAdapter : IAnalyticsAdapter {

    fun initApp()
}

/**
 * Initializes the registered iOS analytics adapter.
 */
fun SXAnalytics.initApp() {
    val adapter = registeredAdapter()
    check(adapter is AnalyticsAdapter) {
        "The registered analytics adapter must implement AnalyticsAdapter"
    }
    adapter.initApp()
}
