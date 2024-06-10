package data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.atomicfu.locks.SynchronizedObject
import kotlinx.atomicfu.locks.synchronized
import okio.Path.Companion.toPath

internal const val dataStoreFileName = "settings.preferences_pb"

object AppSettings {
    private lateinit var datastore: DataStore<Preferences>
    private val lock = SynchronizedObject()

    fun getDataStore(producePath: () -> String): DataStore<Preferences> {
        return synchronized(lock) {
            if(::datastore.isInitialized) {
                datastore
            } else {
                PreferenceDataStoreFactory.createWithPath(
                    produceFile = {producePath().toPath()}
                ).also { datastore = it  }
            }
        }
    }
}