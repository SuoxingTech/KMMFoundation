package dev.suoxing.kmm_analytics

import android.content.Context
import android.os.Bundle
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

    fun initApp(context: Context) {
        FirebaseApp.initializeApp(context)
        init()
    }

    fun setUserProperty(key: String, value: String) {
        Firebase.analytics.setUserProperty(key, value)
    }

    fun updateDefaultParameter(key: String, value: String) {
        Firebase.analytics.setDefaultEventParameters(Bundle().apply {
            putString(key, value)
        })
    }

    fun stopCollection() {
        Firebase.analytics.setAnalyticsCollectionEnabled(false)
    }

    actual fun init() {
        Firebase.analytics.setAnalyticsCollectionEnabled(true)
    }

    /**
     * Convenient log event
     */
    fun logEvent(event: String, vararg params: Pair<String, Any>) {
        logEvent(event, params.toMap())
    }

    actual fun logEvent(event: String, params: Map<String, Any>) {
        runCatching {
            /**
             * `logEvent` can be invoked from somewhere Analytics is not initialized!
             * So, they are wrapped inside `runCatching` to prevent crashes.
             */
            Firebase.analytics.logEvent(event) {
                params.forEach {
                    when (it.value) {
                        is Int -> param(it.key, (it.value as Int).toLong())
                        is Long -> param(it.key, it.value as Long)
                        is Float -> param(it.key, (it.value as Float).toDouble())
                        is Double -> param(it.key, it.value as Double)
                        else -> param(it.key, it.value.toString())
                    }
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