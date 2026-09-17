package dev.suoxing.kmm_analytics

/**
 * Connects [SXCrashlytics] to a crash reporting SDK owned by the host application.
 *
 * Register the implementation during application startup before errors are reported.
 */
interface ICrashlyticsAdapter {

    fun setCollectionEnabled(enabled: Boolean)

    fun reportNonFatal(throwable: Throwable)
}

internal object EmptyICrashlyticsAdapter : ICrashlyticsAdapter {

    override fun setCollectionEnabled(enabled: Boolean) = Unit

    override fun reportNonFatal(throwable: Throwable) = Unit
}
