package dev.suoxing.kmm_arch.kv

import platform.Foundation.NSUserDefaults
import platform.Foundation.setValue

actual open class AppKV actual constructor(): IAppKV {

    private val defaults by lazy {
        NSUserDefaults.standardUserDefaults
    }

    override fun putInt(key: String, value: Int) {
        // value: platform.darwin.NSInteger /* = kotlin.Long */
        putLong(key, value.toLong())
    }

    override fun putString(key: String, value: String) {
        defaults.setObject(value, key)
    }

    override fun putBoolean(key: String, value: Boolean) {
        defaults.setBool(value, key)
    }

    override fun putFloat(key: String, value: Float) {
        defaults.setFloat(value, key)
    }

    override fun putDouble(key: String, value: Double) {
        defaults.setDouble(value, key)
    }

    override fun putLong(key: String, value: Long) {
        defaults.setInteger(value, key)
    }

    override fun putStrings(key: String, value: Set<String>) {
        defaults.setObject(value.toList(), key)
    }

    override fun getInt(key: String, defValue: Int): Int = (get(key, defValue.toLong())).toInt()

    override fun getString(key: String, defValue: String): String = get(key, defValue)

    override fun getBoolean(key: String, defValue: Boolean): Boolean = get(key, defValue)

    override fun getFloat(key: String, defValue: Float): Float = get(key, defValue)

    override fun getDouble(key: String, defValue: Double): Double = get(key, defValue)

    override fun getLong(key: String, defValue: Long): Long = get(key, defValue)

    override fun getStrings(key: String, defValue: Set<String>): Set<String> {
        return defaults.stringArrayForKey(key)?.mapNotNull {
            it as? String
        }?.toSet() ?: defValue
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> get(key: String, defValue: T): T {
        return (defaults.objectForKey(key) as? T) ?: defValue
    }
}