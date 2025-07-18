// CLASSE DE DATASTORE PARA ARMAZENAR OS DADOS
package com.example.petapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class SettingsDataStore(context: Context) {
    private val dataStore = context.dataStore
    companion object {
        // chave para salvar o estado das notificações on/off
        val NOTIFICATIONS_ENABLED_KEY = booleanPreferencesKey("notifications_enabled")
        // chave para salvar o modo escuro
        val DARK_MODE_ENABLED_KEY = booleanPreferencesKey("dark_mode_enabled")
    }
    val notificationsEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[NOTIFICATIONS_ENABLED_KEY] ?: true // valor padrão é notificações habilitadas
    }
    val darkModeEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[DARK_MODE_ENABLED_KEY] ?: false // valor padrão é modo claro
    }
    suspend fun setNotificationsEnabled(isEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[NOTIFICATIONS_ENABLED_KEY] = isEnabled
        }
    }
    suspend fun setDarkModeEnabled(isEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_MODE_ENABLED_KEY] = isEnabled
        }
    }
}