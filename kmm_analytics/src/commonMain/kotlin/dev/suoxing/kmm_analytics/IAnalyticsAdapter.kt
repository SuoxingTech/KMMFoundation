package dev.suoxing.kmm_analytics

/**
 * Connects [SXAnalytics] to an analytics SDK owned by the host application.
 *
 * Register the implementation during application startup before events are emitted.
 */
interface IAnalyticsAdapter {

    fun setCollectionEnabled(enabled: Boolean)

    fun setUserProperty(key: String, value: String)

    fun updateDefaultParameter(key: String, value: String)

    fun logEvent(event: String, params: Map<String, Any>)

    fun logPurchase(currency: String, value: Double, itemId: String)
}

internal object EmptyIAnalyticsAdapter : IAnalyticsAdapter {

    override fun setCollectionEnabled(enabled: Boolean) = Unit

    override fun setUserProperty(key: String, value: String) = Unit

    override fun updateDefaultParameter(key: String, value: String) = Unit

    override fun logEvent(event: String, params: Map<String, Any>) = Unit

    override fun logPurchase(currency: String, value: Double, itemId: String) = Unit
}
