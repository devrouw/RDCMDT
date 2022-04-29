package com.lollipop.rdcmdt.repository.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private object PreferenceKeys{
    val token = stringPreferencesKey("token")
    val username = stringPreferencesKey("username")
    val accountNo = stringPreferencesKey("accountNo")
}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class DataStoreRepository @Inject constructor(
    @ApplicationContext context: Context
) : IDataStoreRepository {
    private val dataStore = context.dataStore

    override suspend fun saveAuth(token: String, username: String, accountNo: String) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.token] = token
            preference[PreferenceKeys.username] = username
            preference[PreferenceKeys.accountNo] = accountNo
        }
    }

    override suspend fun resetAuth() {
        dataStore.edit { preference ->
            preference[PreferenceKeys.token] = "-"
            preference[PreferenceKeys.username] = "-"
            preference[PreferenceKeys.accountNo] = "-"
        }
    }

    val readAuth: Flow<List<String>> = dataStore.data
        .catch { exception ->
            if(exception is IOException){
                Timber.d(exception.message.toString())
            }else{
                throw exception
            }
        }
        .map { preferences ->
            val auth: MutableList<String> = mutableListOf()
            val token: String = preferences[PreferenceKeys.token] ?: "-"
            val username: String = preferences[PreferenceKeys.username] ?: "-"
            val accountNo: String = preferences[PreferenceKeys.accountNo] ?: "-"
            auth.add(token)
            auth.add(username)
            auth.add(accountNo)
            auth
        }

}