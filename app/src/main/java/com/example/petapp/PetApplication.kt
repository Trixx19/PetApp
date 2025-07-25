package com.example.petapp

import android.app.Application
import com.example.petapp.auth.data.AuthRepository // IMPORTAR
import com.example.petapp.data.PetDatabase
import com.example.petapp.data.PetRepository
import com.example.petapp.data.SettingsDataStore
import com.example.petapp.data.ThemePreferences

class PetApplication : Application() {
    // --- REPOSITÓRIOS ---
    // O banco de dados e o repositório que você já tinha
    private val database: PetDatabase by lazy { PetDatabase.getDatabase(this) }
    val repository: PetRepository by lazy { PetRepository(database.petDao()) }
    // O novo repositório para autenticação
    val authRepository: AuthRepository by lazy { AuthRepository() } // ADICIONAR ESTA LINHA

    // --- DATASTORES ---
    // Criando a instância para salvar as configurações
    val settingsDataStore: SettingsDataStore by lazy { SettingsDataStore(this) }
    // Criando a instância para salvar o tema
    val themePreferences: ThemePreferences by lazy { ThemePreferences(this) }
}