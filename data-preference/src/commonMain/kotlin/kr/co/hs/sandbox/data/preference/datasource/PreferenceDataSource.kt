package kr.co.hs.sandbox.data.preference.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

internal abstract class PreferenceDataSource {
    companion object {
        const val PREFERENCE_FILE_NAME = "sandbox.preferences_pb"
    }

    abstract fun getPreferenceDataStore(): DataStore<Preferences>

    fun flowOfEdit(key: String, value: Int): Flow<Int> = flowOf(value)
        .onEach { number -> getPreferenceDataStore().edit { it[intPreferencesKey(key)] = number } }

    fun flowOfData(key: String, default: Int): Flow<Int> = getPreferenceDataStore()
        .data
        .map { it[intPreferencesKey(key)] ?: default }
}