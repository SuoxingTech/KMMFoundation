package dev.suoxing.kmm_analytics

/**
 * Provider-independent crash reporting entry point.
 *
 * The host application supplies a [ICrashlyticsAdapter] during startup.
 */
object SXCrashlytics {

    private var adapter: ICrashlyticsAdapter = EmptyICrashlyticsAdapter

    fun register(adapter: ICrashlyticsAdapter) {
        this.adapter = adapter
    }

    internal fun registeredAdapter(): ICrashlyticsAdapter = adapter

    fun setCollectionEnabled(enabled: Boolean) {
        adapter.setCollectionEnabled(enabled)
    }

    fun enable() {
        setCollectionEnabled(true)
    }

    fun disable() {
        setCollectionEnabled(false)
    }

    fun reportNonFatal(throwable: Throwable) {
        adapter.reportNonFatal(throwable)
    }
}
