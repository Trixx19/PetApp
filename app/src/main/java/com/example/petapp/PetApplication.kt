package com.example.petapp

import android.app.Application
import com.example.petapp.data.PetDatabase
import com.example.petapp.data.PetRepository
import com.example.petapp.data.SettingsDataStore
import com.example.petapp.data.ThemePreferences

class PetApplication : Application() {
    // O banco de dados e o repositório que você já tinha
    private val database: PetDatabase by lazy { PetDatabase.getDatabase(this) }
    val repository: PetRepository by lazy { PetRepository(database.petDao()) }

    // ## AS DUAS LINHAS QUE FALTAVAM ##
    // Criando a instância para salvar as configurações
    val settingsDataStore: SettingsDataStore by lazy { SettingsDataStore(this) }
    // Criando a instância para salvar o tema
    val themePreferences: ThemePreferences by lazy { ThemePreferences(this) }
}