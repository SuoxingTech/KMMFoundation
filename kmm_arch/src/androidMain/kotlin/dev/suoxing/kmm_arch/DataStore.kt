package dev.suoxing.kmm_arch

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

// TODO: ability to seprate kv storage into different files/domains

const val PREFERENCES_NAME_APP = "app_pref"

//fun Context.dataStore(name: String = PREFERENCES_NAME_APP): DataStore<Preferences> {
//
//}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("")