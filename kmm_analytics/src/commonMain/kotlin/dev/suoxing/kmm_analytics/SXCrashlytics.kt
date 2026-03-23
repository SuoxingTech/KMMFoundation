package dev.suoxing.kmm_analytics

expect object SXCrashlytics: ISXCrashlytics {
    override fun enable()

    override fun disable()
}
