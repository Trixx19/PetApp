package com.example.petapp

import android.app.Application
import com.example.petapp.auth.data.AuthRepository
import com.example.petapp.data.PetDatabase
import com.example.petapp.data.PetRepository
import com.example.petapp.data.SettingsDataStore
import com.example.petapp.data.ThemePreferences

class PetApplication : Application() {
    private val database: PetDatabase by lazy { PetDatabase.getDatabase(this) }
    // Passa o contexto da aplicação para o repositório
    val repository: PetRepository by lazy { PetRepository(database.petDao(), this) }
    val authRepository: AuthRepository by lazy { AuthRepository() }

    val settingsDataStore: SettingsDataStore by lazy { SettingsDataStore(this) }
    val themePreferences: ThemePreferences by lazy { ThemePreferences(this) }
}