@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package dev.suoxing.kmm_analytics

import cocoapods.FirebaseCore.FIRApp

internal fun ensureFirebaseConfigured() {
    if (FIRApp.defaultApp() == null) {
        FIRApp.configure()
    }
}

internal fun Map<String, Any>.toFirebaseParams(): Map<Any?, *> = mapValues { (_, value) ->
    value.toFirebaseParamValue()
}

private fun Any.toFirebaseParamValue(): Any = when (this) {
    is String -> this
    is Double -> this
    is Float -> this.toDouble()
    is Int -> this.toLong()
    is Long -> this
    is Boolean -> this
    else -> this.toString()
}
