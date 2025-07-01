// CLASSE DE PREFERÊNCIAS DE TEMA
package com.example.petapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.petapp.ui.theme.ThemeVariant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.themeDataStore: DataStore<Preferences> by preferencesDataStore(name = "theme_preferences")

class ThemePreferences(private val context: Context) {
    // chave para armazenar a preferência de tema no DataStore
    private val THEME_VARIANT_KEY = stringPreferencesKey("theme_variant")
    val themeVariant: Flow<ThemeVariant> = context.themeDataStore.data
        .map { preferences -> ThemeVariant.valueOf(
                preferences[THEME_VARIANT_KEY] ?: ThemeVariant.MONOCHROME.name
            )
        }
    // função para salvar a preferência de tema no DataStore
    suspend fun saveThemeVariant(themeVariant: ThemeVariant) {
        context.themeDataStore.edit { preferences ->
            preferences[THEME_VARIANT_KEY] = themeVariant.name
        }
    }
}