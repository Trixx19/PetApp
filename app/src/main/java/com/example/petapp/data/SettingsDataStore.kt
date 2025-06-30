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

// Cria uma instância do DataStore para o nosso app
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsDataStore(context: Context) {

    private val dataStore = context.dataStore

    companion object {
        // Chave para salvar o estado das notificações (ativado/desativado)
        val NOTIFICATIONS_ENABLED_KEY = booleanPreferencesKey("notifications_enabled")
        val THEME_PALETTE_KEY = stringPreferencesKey("theme_palette")
    }

    // Fluxo para ler o valor salvo. O valor padrão é 'true' (notificações ativadas)
    val notificationsEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[NOTIFICATIONS_ENABLED_KEY] ?: true
    }

    // Função para salvar o novo estado do interruptor
    suspend fun setNotificationsEnabled(isEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[NOTIFICATIONS_ENABLED_KEY] = isEnabled
        }
    }

    // Fluxo para ler a paleta de cores. O valor padrão é "PURPLE".
    val themePalette: Flow<String> = dataStore.data.map { preferences ->
        preferences[THEME_PALETTE_KEY] ?: "PURPLE"
    }

    // Função para salvar a nova paleta escolhida
    suspend fun setThemePalette(paletteName: String) {
        dataStore.edit { preferences ->
            preferences[THEME_PALETTE_KEY] = paletteName
        }
    }
}