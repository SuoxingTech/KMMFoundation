package dev.suoxing.kmm_kv

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

actual open class AppKV actual constructor() {

    /**
     * Initialize `AppKV` with provided `Application`.
     * Call `init` when application creates.
     */
    fun init(application: Application) {
        dataStore = application.dataStore
    }

    private lateinit var dataStore: DataStore<Preferences>

    private fun <T> edit(key: Preferences.Key<T>, value: T) {
        runBlocking {
            dataStore.edit {
                it[key] = value
            }
        }
    }

    /**
     * Preload DataStore on main entries to speed up further reading
     * operation.(Since it will cache data into memory)
     *
     * Usage:
     * onCreate() {
     *     lifecycleScope.launch {
     *         AppKV.preloadDataStore()
     *     }
     * }
     */
    suspend fun preloadDataStore() {
        dataStore.data.first()
    }

    /**
     * Clear DataStore values.
     *
     * @param ignoreKeys key names that needs to be retained from remove operation
     */
    suspend fun clear(ignoreKeys: List<String>) {
        val keys = dataStore.data.first().asMap().keys
        dataStore.edit { prefs ->
            keys.filter {
                !ignoreKeys.contains(it.name)
            }.forEach {
                prefs.remove(it)
            }
        }
    }

    /**
     * Reading value synchronously.
     * To reduce performance shortage, please call [preloadDataStore] on main entries
     * of your App's user journey.
     */
    private fun <T> get(key: Preferences.Key<T>, defValue: T): T {
        return runBlocking {
            dataStore.data.first()[key] ?: defValue
        }
    }

    actual fun putInt(key: String, value: Int) {
        val prefKey = intPreferencesKey(key)
        edit(prefKey, value)
    }

    actual fun putString(key: String, value: String) {
        val prefKey = stringPreferencesKey(key)
        edit(prefKey, value)
    }

    actual fun putBoolean(key: String, value: Boolean) {
        val prefKey = booleanPreferencesKey(key)
        edit(prefKey, value)
    }

    actual fun putFloat(key: String, value: Float) {
        val prefKey = floatPreferencesKey(key)
        edit(prefKey, value)
    }

    actual fun putDouble(key: String, value: Double) {
        val prefKey = doublePreferencesKey(key)
        edit(prefKey, value)
    }

    actual fun putLong(key: String, value: Long) {
        val prefKey = longPreferencesKey(key)
        edit(prefKey, value)
    }

    actual fun putStrings(key: String, value: Set<String>) {
        val prefKey = stringSetPreferencesKey(key)
        edit(prefKey, value)
    }

    actual fun getInt(key: String, defValue: Int): Int
        = get(intPreferencesKey(key), defValue)

    actual fun getString(key: String, defValue: String): String
        = get(stringPreferencesKey(key), defValue)

    actual fun getBoolean(key: String, defValue: Boolean): Boolean
        = get(booleanPreferencesKey(key), defValue)

    actual fun getFloat(key: String, defValue: Float): Float
        = get(floatPreferencesKey(key), defValue)

    actual fun getDouble(key: String, defValue: Double): Double
        = get(doublePreferencesKey(key), defValue)

    actual fun getLong(key: String, defValue: Long): Long
        = get(longPreferencesKey(key), defValue)

    actual fun getStrings(key: String, defValue: Set<String>): Set<String>
        = get(stringSetPreferencesKey(key), defValue)
}