@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package dev.suoxing.kmm_analytics

import cocoapods.FirebaseAnalytics.FIRAnalytics

/**
 * SXAnalytics iOS implementation.
 *
 * @author Yifan Wong
 * @since 2022-09-24
 */
actual object SXAnalytics: ISXAnalytics {

    actual override fun init() {
        ensureFirebaseConfigured()
        FIRAnalytics.setAnalyticsCollectionEnabled(true)
    }

    actual override fun logEvent(event: String, params: Map<String, Any>) {
        ensureFirebaseConfigured()
        FIRAnalytics.logEventWithName(event, params.toFirebaseParams())
    }

    actual override fun logClickEvent(params: Map<String, Any>) {
        logEvent(SXAnalyticsEvents.CLICK, params)
    }

    actual override fun logLongClickEvent(params: Map<String, Any>) {
        logEvent(SXAnalyticsEvents.LONG_CLICK, params)
    }

    actual override fun logViewEvent(params: Map<String, Any>) {
        logEvent(SXAnalyticsEvents.VIEW, params)
    }

    actual override fun logViewEndEvent(params: Map<String, Any>) {
        logEvent(SXAnalyticsEvents.VIEW_END, params)
    }

    actual override fun logImpressEvent(params: Map<String, Any>) {
        logEvent(SXAnalyticsEvents.IMPRESS, params)
    }

    actual override fun logImpressEndEvent(params: Map<String, Any>) {
        logEvent(SXAnalyticsEvents.IMPRESS_END, params)
    }
}
