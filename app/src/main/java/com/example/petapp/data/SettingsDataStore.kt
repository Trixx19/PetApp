// app/src/main/java/com/example/petapp/data/SettingsDataStore.kt
package com.example.petapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
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
        // --- NOVA CHAVE: Para salvar o estado do modo escuro ---
        val DARK_MODE_ENABLED_KEY = booleanPreferencesKey("dark_mode_enabled")
    }

    // Fluxo para ler o valor salvo das notificações. O valor padrão é 'true'
    val notificationsEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[NOTIFICATIONS_ENABLED_KEY] ?: true
    }

    // --- NOVO FLUXO: Para ler o valor salvo do modo escuro ---
    val darkModeEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[DARK_MODE_ENABLED_KEY] ?: false // Valor padrão: modo claro
    }

    // Função para salvar o novo estado do interruptor de notificações
    suspend fun setNotificationsEnabled(isEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[NOTIFICATIONS_ENABLED_KEY] = isEnabled
        }
    }

    // --- NOVA FUNÇÃO: Para salvar o novo estado do interruptor do modo escuro ---
    suspend fun setDarkModeEnabled(isEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_MODE_ENABLED_KEY] = isEnabled
        }
    }
}