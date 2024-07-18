package dev.suoxing.kmm_analytics

import cocoapods.FirebaseAnalytics.FIRAnalytics
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * SXAnalytics iOS implementation.
 *
 * @author Yifan Wong
 * @since 2022-09-24
 */
actual object SXAnalytics: ISXAnalytics {

    @OptIn(ExperimentalForeignApi::class)
    actual override fun init() {
        FIRAnalytics.initialize()
    }

    @OptIn(ExperimentalForeignApi::class)
    actual override fun logEvent(event: String, params: Map<String, Any>) {
        FIRAnalytics.logEventWithName(event, params.mapValues { it })
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