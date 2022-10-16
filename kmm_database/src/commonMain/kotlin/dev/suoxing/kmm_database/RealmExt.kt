package dev.suoxing.kmm_database

import io.realm.kotlin.query.RealmQuery
import io.realm.kotlin.query.RealmSingleQuery
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * RealmExt.kt
 *
 * @author Yifan Wong
 * @since 2022-08-20
 */

/**
 * Simplify the process of responding to changes in realm query results.
 *
 * **See Also:** [Realm doc](https://www.mongodb.com/docs/realm/sdk/kotlin/realm-database/react-to-changes/#register-a-query-change-listener)
 */
fun <T: RealmObject> RealmQuery<T>.flowQuery(): Flow<List<T>> {
    return this.asFlow().map {
        it.list.toList()
    }
}

/**
 * Simplify the process of responding to changes in a specific [RealmObject].
 *
 * **See Also:** [Realm doc](https://www.mongodb.com/docs/realm/sdk/kotlin/realm-database/react-to-changes/#register-a-realmobject-change-listener)
 */
fun <T: RealmObject> RealmSingleQuery<T>.flowSingle(): Flow<T?> {
    return this.asFlow().map {
        it.obj
    }
}

/**
 * Simplify the process of responding to changes in a list of [RealmObject]s
 * within another [RealmObject].
 *
 * **See Also:** [Realm doc](https://www.mongodb.com/docs/realm/sdk/kotlin/realm-database/react-to-changes/#register-a-realmlist-change-listener)
 */
fun <T: RealmObject> RealmList<T>.flowNestedList(): Flow<List<T>> {
    return this.asFlow().map {
        it.list.toList()
    }
}