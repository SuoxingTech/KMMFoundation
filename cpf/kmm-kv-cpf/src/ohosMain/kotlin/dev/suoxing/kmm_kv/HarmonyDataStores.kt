package dev.suoxing.kmm_kv

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okio.Path.Companion.toPath

/** Application-lifetime stores shared by every AppKV instance using the same path. */
internal object HarmonyDataStores {
    private val mutex = Mutex()
    private val stores = mutableMapOf<String, DataStore<Preferences>>()

    fun open(filePath: String): DataStore<Preferences> {
        val path = filePath.toPath(normalize = true)
        require(path.isAbsolute) { "Preferences path must be absolute" }
        require(path.name.endsWith(".preferences_pb")) { "Preferences file must end in .preferences_pb" }
        return runBlocking(Dispatchers.IO) {
            mutex.withLock {
                stores.getOrPut(path.toString()) {
                    PreferenceDataStoreFactory.createWithPath(
                        scope = CoroutineScope(SupervisorJob() + Dispatchers.IO),
                        produceFile = { path },
                    )
                }
            }
        }
    }
}
