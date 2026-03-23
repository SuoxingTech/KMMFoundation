@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package dev.suoxing.kmm_analytics

import cocoapods.FirebaseCrashlytics.FIRCrashlytics

actual object SXCrashlytics: ISXCrashlytics {

    actual override fun enable() {
        ensureFirebaseConfigured()
        FIRCrashlytics.crashlytics().setCrashlyticsCollectionEnabled(true)
    }

    actual override fun disable() {
        ensureFirebaseConfigured()
        FIRCrashlytics.crashlytics().setCrashlyticsCollectionEnabled(false)
    }
}
