package com.alwan.todolist.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.core.remove
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class UserPreferences(context: Context) {
    private val applicationContext = context.applicationContext
    private val dataStore: DataStore<Preferences>

    init{
        dataStore = applicationContext.createDataStore(
            name = "note_data_store"
        )
    }

    val authId: Flow<String?>
        get() = dataStore.data.map {
        it[ID_AUTH]
    }

    suspend fun saveAuthId(authId: String){
        dataStore.edit {
            it[ID_AUTH] = authId
        }
    }

    suspend fun deleteAuthId(){
        dataStore.edit {
            it.remove(ID_AUTH)
        }
    }

    companion object{
        private val ID_AUTH = preferencesKey<String>(name = "id_auth")
    }
}