package dev.suoxing.kmm_arch.kv

import androidx.datastore.preferences.core.*
import dev.suoxing.kmm_arch.ApplicationWrapper
import dev.suoxing.kmm_arch.dataStore
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

actual open class AppKV actual constructor(): IAppKV {

    private val dataStore = ApplicationWrapper.instance.dataStore

    private fun <T> edit(key: Preferences.Key<T>, value: T) {
        MainScope().launch {
            dataStore.edit {
                it[key] = value
            }
        }
    }

    /**
     * Temporary felt unhappy of reading value synchronously 😭😭😭
     */
    private fun <T> get(key: Preferences.Key<T>, defValue: T): T {
        return runBlocking {
            dataStore.data.first()[key] ?: defValue
        }
    }

    override fun putInt(key: String, value: Int) {
        val prefKey = intPreferencesKey(key)
        edit(prefKey, value)
    }

    override fun putString(key: String, value: String) {
        val prefKey = stringPreferencesKey(key)
        edit(prefKey, value)
    }

    override fun putBoolean(key: String, value: Boolean) {
        val prefKey = booleanPreferencesKey(key)
        edit(prefKey, value)
    }

    override fun putFloat(key: String, value: Float) {
        val prefKey = floatPreferencesKey(key)
        edit(prefKey, value)
    }

    override fun putDouble(key: String, value: Double) {
        val prefKey = doublePreferencesKey(key)
        edit(prefKey, value)
    }

    override fun putLong(key: String, value: Long) {
        val prefKey = longPreferencesKey(key)
        edit(prefKey, value)
    }

    override fun putStrings(key: String, value: Set<String>) {
        val prefKey = stringSetPreferencesKey(key)
        edit(prefKey, value)
    }

    override fun getInt(key: String, defValue: Int): Int
        = get(intPreferencesKey(key), defValue)

    override fun getString(key: String, defValue: String): String
        = get(stringPreferencesKey(key), defValue)

    override fun getBoolean(key: String, defValue: Boolean): Boolean
        = get(booleanPreferencesKey(key), defValue)

    override fun getFloat(key: String, defValue: Float): Float
        = get(floatPreferencesKey(key), defValue)

    override fun getDouble(key: String, defValue: Double): Double
        = get(doublePreferencesKey(key), defValue)

    override fun getLong(key: String, defValue: Long): Long
        = get(longPreferencesKey(key), defValue)

    override fun getStrings(key: String, defValue: Set<String>): Set<String>
        = get(stringSetPreferencesKey(key), defValue)
}