package dev.suoxing.kmm_database

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.types.BaseRealmObject
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

    /**
     * Get [Realm] object reference by lazy.
     */
    val realm: Realm by lazy {
        val config = RealmConfiguration.Builder(models).build()
        isOpen = true
        Realm.open(config)
    }

    /**
     * Close a [Realm] database connection.
     */
    fun close() {
        if (isOpen) {
            realm.close()
        }
    }

    /**
     * Define [Realm] models. Should be subclass of [BaseRealmObject].
     */
    abstract val models: Set<KClass<out BaseRealmObject>>
}