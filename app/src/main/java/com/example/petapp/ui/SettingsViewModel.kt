package com.example.petapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.petapp.PetApplication
import com.example.petapp.data.PetRepository
import com.example.petapp.data.SettingsDataStore
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

    val darkModeEnabled: StateFlow<Boolean> = settingsDataStore.darkModeEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val themeVariant: StateFlow<ThemeVariant> = themePreferences.themeVariant
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ThemeVariant.BLUE_ACCENT)

    fun setNotificationsEnabled(isEnabled: Boolean) {
        viewModelScope.launch {
            settingsDataStore.setNotificationsEnabled(isEnabled)
        }
    }

    fun setDarkModeEnabled(isEnabled: Boolean) {
        viewModelScope.launch {
            settingsDataStore.setDarkModeEnabled(isEnabled)
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
                // Agora sim, a 'application' tem todas as dependências necessárias
                return SettingsViewModel(
                    application.settingsDataStore,
                    application.themePreferences,
                    application.repository
                ) as T
            }
        }
    }
}