package dev.suoxing.kmm_analytics

/**
 * Provider-independent analytics entry point.
 *
 * The host application supplies an [IAnalyticsAdapter] during startup.
 */
object SXAnalytics {

    private var adapter: IAnalyticsAdapter = EmptyIAnalyticsAdapter

    fun register(adapter: IAnalyticsAdapter) {
        this.adapter = adapter
    }

    internal fun registeredAdapter(): IAnalyticsAdapter = adapter

    fun stopCollection() {
        adapter.setCollectionEnabled(false)
    }

    fun startCollection() {
        adapter.setCollectionEnabled(true)
    }

    fun logEvent(event: String, params: Map<String, Any> = emptyMap()) {
        adapter.logEvent(event, params)
    }

    fun setUserProperty(key: String, value: String) {
        adapter.setUserProperty(key, value)
    }

    fun updateDefaultParameter(key: String, value: String) {
        adapter.updateDefaultParameter(key, value)
    }

    fun logPurchase(currency: String, value: Double, itemId: String) {
        adapter.logPurchase(currency, value, itemId)
    }
}
