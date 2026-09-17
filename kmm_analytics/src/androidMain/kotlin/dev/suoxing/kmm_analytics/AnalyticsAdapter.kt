package dev.suoxing.kmm_analytics

import android.content.Context

/**
 * Android analytics adapter with explicit SDK initialization.
 */
interface AnalyticsAdapter : IAnalyticsAdapter {

    fun initApp(context: Context)
}

/**
 * Initializes the registered Android analytics adapter with [context].
 */
fun SXAnalytics.initApp(context: Context) {
    val adapter = registeredAdapter()
    check(adapter is AnalyticsAdapter) {
        "The registered analytics adapter must implement AnalyticsAdapter"
    }
    adapter.initApp(context)
}
