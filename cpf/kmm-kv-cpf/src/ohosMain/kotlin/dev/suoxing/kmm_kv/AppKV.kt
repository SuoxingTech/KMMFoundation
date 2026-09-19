package dev.suoxing.kmm_kv

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.IO

actual open class AppKV actual constructor() {

    /** Initialize during application startup with an absolute *.preferences_pb file path. */
    fun init(filePath: String) {
        val store = HarmonyDataStores.open(filePath)
        check(dataStore == null || dataStore === store) { "AppKV is already initialized with another file" }
        dataStore = store
    }

    private var dataStore: DataStore<Preferences>? = null
    private val store: DataStore<Preferences>
        get() = checkNotNull(dataStore) { "Call AppKV.init(filePath) before using preferences" }

    private fun <T> edit(key: Preferences.Key<T>, value: T) {
        runBlocking(kotlinx.coroutines.Dispatchers.IO) {
            store.edit {
                it[key] = value
            }
        }
    }

    /** Preload during startup to reduce the first synchronous read latency. */
    suspend fun preloadDataStore() {
        store.data.first()
    }

    /**
     * Clear DataStore values.
     *
     * @param ignoreKeys key names that needs to be retained from remove operation
     */
    suspend fun clear(ignoreKeys: List<String>) {
        store.edit { prefs ->
            val keys = prefs.asMap().keys
            keys.filter {
                !ignoreKeys.contains(it.name)
            }.forEach {
                prefs.remove(it)
            }
        }
    }

    actual fun delete(key: String) {
        runBlocking(kotlinx.coroutines.Dispatchers.IO) {
            store.edit { prefs ->
                prefs.asMap().keys
                    .firstOrNull { it.name == key }
                    ?.let {
                        prefs.remove(it)
                    }
            }
        }
    }

    /**
     * Reading value synchronously.
     * To reduce performance shortage, please call [preloadDataStore] on main entries
     * of your App's user journey.
     */
    private fun <T> get(key: Preferences.Key<T>, defValue: T): T {
        return runBlocking(kotlinx.coroutines.Dispatchers.IO) {
            store.data.first()[key] ?: defValue
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
