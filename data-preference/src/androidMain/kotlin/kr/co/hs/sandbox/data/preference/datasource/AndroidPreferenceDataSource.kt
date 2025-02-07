package kr.co.hs.sandbox.data.preference.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

internal class AndroidPreferenceDataSource : PreferenceDataSource(), KoinComponent {

    private val context: Context = get()

    private val dataStore by lazy {
        PreferenceDataStoreFactory.createWithPath(
            produceFile = {
                context.filesDir.resolve(PREFERENCE_FILE_NAME).absolutePath.toPath()
            }
        )
    }

    override fun getPreferenceDataStore(): DataStore<Preferences> = dataStore
}