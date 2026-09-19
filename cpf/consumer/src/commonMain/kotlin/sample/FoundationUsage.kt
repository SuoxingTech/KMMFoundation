package sample

import dev.suoxing.kmm_arch.viewmodel.ViewModel
import dev.suoxing.kmm_kv.AppKV
import dev.suoxing.kmm_analytics.SXAnalytics
import kotlinx.coroutines.flow.MutableStateFlow

class PreferencesViewModel(private val preferences: AppKV) : ViewModel<String>() {
    override val _uiStateFlow = MutableStateFlow(preferences.getString("label", ""))

    fun updateLabel(value: String) {
        preferences.putString("label", value)
        _uiStateFlow.value = value
        SXAnalytics.logEvent("label_updated")
    }
}
