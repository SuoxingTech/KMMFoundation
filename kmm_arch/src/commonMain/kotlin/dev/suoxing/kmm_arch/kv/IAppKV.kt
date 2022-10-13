package dev.suoxing.kmm_arch.kv

interface IAppKV {

    fun putInt(key: String, value: Int)
    fun putString(key: String, value: String)
    fun putBoolean(key: String, value: Boolean)
    fun putFloat(key: String, value: Float)
    fun putDouble(key: String, value: Double)
    fun putLong(key: String, value: Long)
    fun putStrings(key: String, value: Set<String>)

    fun getInt(key: String, defValue: Int): Int
    fun getString(key: String, defValue: String): String
    fun getBoolean(key: String, defValue: Boolean): Boolean
    fun getFloat(key: String, defValue: Float): Float
    fun getDouble(key: String, defValue: Double): Double
    fun getLong(key: String, defValue: Long): Long
    fun getStrings(key: String): Set<String> = getStrings(key, emptySet())
    fun getStrings(key: String, defValue: Set<String> = emptySet()): Set<String>
}