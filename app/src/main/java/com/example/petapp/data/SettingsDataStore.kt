package com.example.petapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

// Enum para representar as opções de tema
enum class ThemeMode {
    LIGHT, DARK, SYSTEM
}

class SettingsDataStore(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        val NOTIFICATIONS_ENABLED_KEY = booleanPreferencesKey("notifications_enabled")
        // A chave DARK_MODE_ENABLED_KEY será substituída pela nova chave de string
        val THEME_MODE_KEY = stringPreferencesKey("theme_mode")
    }

    val notificationsEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[NOTIFICATIONS_ENABLED_KEY] ?: true
    }

    // Fluxo para ler a preferência do modo de tema
    val themeMode: Flow<ThemeMode> = dataStore.data.map { preferences ->
        // Lê a string e converte para o enum. O padrão é SYSTEM.
        ThemeMode.valueOf(preferences[THEME_MODE_KEY] ?: ThemeMode.SYSTEM.name)
    }

    suspend fun setNotificationsEnabled(isEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[NOTIFICATIONS_ENABLED_KEY] = isEnabled
        }
    }

    // Função para salvar a preferência do modo de tema
    suspend fun setThemeMode(themeMode: ThemeMode) {
        dataStore.edit { preferences ->
            preferences[THEME_MODE_KEY] = themeMode.name
        }
    }
}