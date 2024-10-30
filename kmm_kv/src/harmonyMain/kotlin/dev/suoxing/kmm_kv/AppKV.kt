package dev.suoxing.kmm_kv

actual open class AppKV actual constructor() {

    private var pref: Preferences? = null

    fun initialize(context: Context) {
        pref = kotlin.runCatching {
            Preferences.getPreferencesSync(context, object : Preferences.Options {
                override val name: String = "travelogApp"
                override val dataGroupId: String? = null
            })
        }.getOrNull()
        if (pref == null) {
            println("ERROR initializing Preferences!!!")
        }
    }

    actual fun putInt(key: String, value: Int) {
        pref?.putSync(key, value)
    }

    actual fun putString(key: String, value: String) {
        pref?.putSync(key, value)
    }

    actual fun putBoolean(key: String, value: Boolean) {
        pref?.putSync(key, value)
    }

    actual fun putFloat(key: String, value: Float) {
        pref?.putSync(key, value)
    }

    actual fun putDouble(key: String, value: Double) {
        pref?.putSync(key, value)
    }

    actual fun putLong(key: String, value: Long) {
        pref?.putSync(key, value)
    }

    actual fun putStrings(key: String, value: Set<String>) {
        pref?.putSync(key, value)
    }

    actual fun getInt(key: String, defValue: Int): Int {
        return pref?.getSync(key, defValue) ?: defValue
    }

    actual fun getString(key: String, defValue: String): String {
        return pref?.getSync(key, defValue) ?: defValue
    }

    actual fun getBoolean(key: String, defValue: Boolean): Boolean {
        return pref?.getSync(key, defValue) ?: defValue
    }

    actual fun getFloat(key: String, defValue: Float): Float {
        return pref?.getSync(key, defValue) ?: defValue
    }

    actual fun getDouble(key: String, defValue: Double): Double {
        return pref?.getSync(key, defValue) ?: defValue
    }

    actual fun getLong(key: String, defValue: Long): Long {
        return pref?.getSync(key, defValue) ?: defValue
    }

    actual fun getStrings(key: String, defValue: Set<String>): Set<String> {
        return pref?.getSync(key, defValue) ?: defValue
    }
}