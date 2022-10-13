package dev.suoxing.kmm_analytics

interface ISXAnalytics {

    /**
     * Initialize analytics SDK
     */
    fun init()

    /**
     * Track event
     */
    fun logEvent(event: String, params: Map<String, Any>)

    /**
     * Track click event
     */
    fun logClickEvent(params: Map<String, Any>)

    /**
     * Track long click event
     */
    fun logLongClickEvent(params: Map<String, Any>)

    /**
     * Track view event
     */
    fun logViewEvent(params: Map<String, Any>)

    /**
     * Track view end event
     */
    fun logViewEndEvent(params: Map<String, Any>)

    /**
     * Track impress event
     */
    fun logImpressEvent(params: Map<String, Any>)

    /**
     * Track impress end event
     */
    fun logImpressEndEvent(params: Map<String, Any>)
}