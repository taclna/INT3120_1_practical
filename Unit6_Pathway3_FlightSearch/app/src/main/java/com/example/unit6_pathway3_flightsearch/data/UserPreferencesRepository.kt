package com.example.unit6_pathway3_flightsearch.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val USER_PREFERENCES_NAME = "user_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCES_NAME)

data class UserPreferences(
    val searchValues: String = ""
)

object UserPreferencesKeys {
    val SEARCH_VALUE = stringPreferencesKey("search_value")
}

class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {

    suspend fun updateUserPreferences(searchValues: String) {
        dataStore.edit { preferences ->
            preferences[UserPreferencesKeys.SEARCH_VALUE] = searchValues
        }
    }

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences()) else throw exception
        }
        .map { preferences ->
            UserPreferences(
                searchValues = preferences[UserPreferencesKeys.SEARCH_VALUE] ?: ""
            )
        }

    companion object {
        fun create(context: Context): UserPreferencesRepository {
            return UserPreferencesRepository(context.dataStore)
        }
    }
}
