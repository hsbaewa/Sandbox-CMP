package kr.co.hs.sandbox.data.preference.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path.Companion.toPath
import org.koin.core.component.KoinComponent
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

internal class IOSPreferenceDataSource : PreferenceDataSource(), KoinComponent {

    @OptIn(ExperimentalForeignApi::class)
    private val dataStore by lazy {
        PreferenceDataStoreFactory.createWithPath(
            produceFile = {
                val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                    directory = NSDocumentDirectory,
                    inDomain = NSUserDomainMask,
                    appropriateForURL = null,
                    create = false,
                    error = null,
                )
                (requireNotNull(documentDirectory).path + "/$PREFERENCE_FILE_NAME").toPath()
            }
        )
    }

    override fun getPreferenceDataStore(): DataStore<Preferences> = dataStore
}