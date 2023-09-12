package dev.suoxing.kmm_database

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.types.TypedRealmObject
import kotlin.reflect.KClass

/**
 * Base realm DB class.
 * Implement it and make subclass **singleton**.
 *
 * @author Yifan Wong
 * @since 2022-10-16
 */
abstract class RealmDB {

    /**
     * A flag indicating whether the database is open.
     */
    var isOpen = false
        private set

    private val config by lazy {
        RealmConfiguration.Builder(models).build()
    }

    private var _realm: Realm? = null

    /**
     * Get [Realm] object reference by lazy.
     */
    val realm: Realm
        get() {
            val instance = _realm
            return if (instance == null || !isOpen) {
                val newInsance = Realm.open(config)
                _realm = newInsance
                newInsance
            } else {
                instance
            }
        }

    /**
     * Close a [Realm] database connection.
     */
    fun close() {
        realm.close()
        isOpen = false
    }

    /**
     * Delete all data.
     */
    fun deleteRealm() {
        realm.writeBlocking {
            deleteAll()
        }
    }

    /**
     * Define [Realm] models. Should be subclass of [TypedRealmObject].
     */
    abstract val models: Set<KClass<out TypedRealmObject>>
}