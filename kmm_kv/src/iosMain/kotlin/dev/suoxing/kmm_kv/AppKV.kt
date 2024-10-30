package dev.suoxing.kmm_kv

import platform.Foundation.NSUserDefaults

actual open class AppKV actual constructor() {

    private val defaults by lazy {
        NSUserDefaults.standardUserDefaults
    }

    actual fun putInt(key: String, value: Int) {
        // value: platform.darwin.NSInteger /* = kotlin.Long */
        putLong(key, value.toLong())
    }

    actual fun putString(key: String, value: String) {
        defaults.setObject(value, key)
    }

    actual fun putBoolean(key: String, value: Boolean) {
        defaults.setBool(value, key)
    }

    actual fun putFloat(key: String, value: Float) {
        defaults.setFloat(value, key)
    }

    actual fun putDouble(key: String, value: Double) {
        defaults.setDouble(value, key)
    }

    actual fun putLong(key: String, value: Long) {
        defaults.setInteger(value, key)
    }

    actual fun putStrings(key: String, value: Set<String>) {
        defaults.setObject(value.toList(), key)
    }

    actual fun getInt(key: String, defValue: Int): Int = (get(key, defValue.toLong())).toInt()

    actual fun getString(key: String, defValue: String): String = get(key, defValue)

    actual fun getBoolean(key: String, defValue: Boolean): Boolean = get(key, defValue)

    actual fun getFloat(key: String, defValue: Float): Float = get(key, defValue)

    actual fun getDouble(key: String, defValue: Double): Double = get(key, defValue)

    actual fun getLong(key: String, defValue: Long): Long = get(key, defValue)

    actual fun getStrings(key: String, defValue: Set<String>): Set<String> {
        return defaults.stringArrayForKey(key)?.mapNotNull {
            it as? String
        }?.toSet() ?: defValue
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> get(key: String, defValue: T): T {
        return (defaults.objectForKey(key) as? T) ?: defValue
    }
}