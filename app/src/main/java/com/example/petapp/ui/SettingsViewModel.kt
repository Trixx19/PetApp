package com.example.petapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.petapp.PetApplication
import com.example.petapp.data.PetRepository
import com.example.petapp.data.SettingsDataStore
import com.example.petapp.data.ThemeMode // Importe o novo enum
import com.example.petapp.data.ThemePreferences
import com.example.petapp.ui.theme.ThemeVariant
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsDataStore: SettingsDataStore,
    private val themePreferences: ThemePreferences,
    private val petRepository: PetRepository
) : ViewModel() {

    val notificationsEnabled: StateFlow<Boolean> = settingsDataStore.notificationsEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    // O estado agora é do tipo ThemeMode
    val themeMode: StateFlow<ThemeMode> = settingsDataStore.themeMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ThemeMode.SYSTEM)

    val themeVariant: StateFlow<ThemeVariant> = themePreferences.themeVariant
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ThemeVariant.BLUE_ACCENT)

    fun setNotificationsEnabled(isEnabled: Boolean) {
        viewModelScope.launch {
            settingsDataStore.setNotificationsEnabled(isEnabled)
        }
    }

    // A função agora aceita um ThemeMode
    fun setThemeMode(themeMode: ThemeMode) {
        viewModelScope.launch {
            settingsDataStore.setThemeMode(themeMode)
        }
    }

    fun saveThemeVariant(theme: ThemeVariant) {
        viewModelScope.launch {
            themePreferences.saveThemeVariant(theme)
        }
    }

    fun clearFavorites() {
        viewModelScope.launch {
            val favoritePets = petRepository.getFavoritePets().first()
            favoritePets.forEach { pet ->
                petRepository.updatePet(pet.copy(isFavorite = false))
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as PetApplication
                return SettingsViewModel(
                    application.settingsDataStore,
                    application.themePreferences,
                    application.repository
                ) as T
            }
        }
    }
}