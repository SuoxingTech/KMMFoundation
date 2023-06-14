package dev.suoxing.kmm_analytics

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase

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
        init()
    }

    fun setUserProperty(key: String, value: String) {
        Firebase.analytics.setUserProperty(key, value)
    }

    fun stopCollection() {
        Firebase.analytics.setAnalyticsCollectionEnabled(false)
    }

    actual fun init() {
        analytics = Firebase.analytics.apply {
            setAnalyticsCollectionEnabled(true)
        }
    }

    actual fun logEvent(event: String, params: Map<String, Any>) {
        analytics?.logEvent(event) {
            params.forEach {
                when (it.value) {
                    is Long -> param(it.key, it.value as Long)
                    is Double -> param(it.key, it.value as Double)
                    else -> param(it.key, it.value.toString())
                }
            }
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
}