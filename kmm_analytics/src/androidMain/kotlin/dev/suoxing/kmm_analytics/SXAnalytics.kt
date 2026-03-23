package dev.suoxing.kmm_analytics

import android.content.Context
import android.os.Bundle
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics

/**
 * SXAnalytics Android implementation.
 *
 * @author Yifan Wong
 * @since 2022-09-24
 */
actual object SXAnalytics {
    private var analytics: FirebaseAnalytics? = null

    fun initApp(context: Context) {
        FirebaseApp.initializeApp(context)
        analytics = FirebaseAnalytics.getInstance(context)
        init()
    }

    fun setUserProperty(key: String, value: String) {
        withAnalytics {
            setUserProperty(key, value)
        }
    }

    fun updateDefaultParameter(key: String, value: String) {
        withAnalytics {
            setDefaultEventParameters(Bundle().apply {
                putString(key, value)
            })
        }
    }

    fun stopCollection() {
        withAnalytics {
            setAnalyticsCollectionEnabled(false)
        }
    }

    actual fun init() {
        withAnalytics {
            setAnalyticsCollectionEnabled(true)
        }
    }

    fun logPurchase(
        currency: String,
        value: Double,
        itemId: String
    ) {
        logEvent(
            FirebaseAnalytics.Event.PURCHASE,
            FirebaseAnalytics.Param.CURRENCY to currency,
            FirebaseAnalytics.Param.VALUE to value,
            FirebaseAnalytics.Param.ITEM_ID to itemId
        )
    }

    /**
     * Convenient log event
     */
    fun logEvent(event: String, vararg params: Pair<String, Any>) {
        logEvent(event, params.toMap())
    }

    actual fun logEvent(event: String, params: Map<String, Any>) {
        withAnalytics {
            logEvent(event, Bundle().apply {
                params.forEach {
                    when (val value = it.value) {
                        is Int -> putLong(it.key, value.toLong())
                        is Long -> putLong(it.key, value)
                        is Float -> putDouble(it.key, value.toDouble())
                        is Double -> putDouble(it.key, value)
                        else -> putString(it.key, value.toString())
                    }
                }
            })
        }
    }

    actual fun logClickEvent(params: Map<String, Any>) {
        logEvent(SXAnalyticsEvents.CLICK, params)
    }

    actual fun logLongClickEvent(params: Map<String, Any>) {
        logEvent(SXAnalyticsEvents.LONG_CLICK, params)
    }

    actual fun logViewEvent(params: Map<String, Any>) {
        logEvent(SXAnalyticsEvents.VIEW, params)
    }

    actual fun logViewEndEvent(params: Map<String, Any>) {
        logEvent(SXAnalyticsEvents.VIEW_END, params)
    }

    actual fun logImpressEvent(params: Map<String, Any>) {
        logEvent(SXAnalyticsEvents.IMPRESS, params)
    }

    actual fun logImpressEndEvent(params: Map<String, Any>) {
        logEvent(SXAnalyticsEvents.IMPRESS_END, params)
    }

    private inline fun withAnalytics(block: FirebaseAnalytics.() -> Unit) {
        kotlin.runCatching {
            /**
             * `logEvent` can be invoked before Analytics is initialized on Android.
             * Ignore it instead of crashing when the app has not called `initApp(context)` yet.
             */
            analytics?.block()
        }
    }
}
