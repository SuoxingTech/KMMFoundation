package sample

import dev.suoxing.kmm_kv.AppKV

/** Pass the Harmony application's filesDir from the host. */
suspend fun initializePreferences(filesDir: String): AppKV = AppKV().apply {
    init("$filesDir/app.preferences_pb")
    preloadDataStore()
}
