package dev.suoxing.kmm_analytics

import cocoapods.FirebaseCrashlytics.FIRCrashlytics
import kotlinx.cinterop.ExperimentalForeignApi

actual object SXCrashlytics: ISXCrashlytics {

    @OptIn(ExperimentalForeignApi::class)
    override fun enable() {
        FIRCrashlytics.initialize()
    }

    override fun disable() {
    }
}