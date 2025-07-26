package com.example.petapp.ui

import android.content.Context
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.petapp.PetApplication
import com.example.petapp.data.ImageStorageManager
import com.example.petapp.data.PetRepository
import com.example.petapp.data.model.Pet
import com.example.petapp.data.model.Reminder
import com.example.petapp.ui.navigation.PetDestinations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PetViewModel(
    private val repository: PetRepository,
    private val context: Context,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val imageStorageManager = ImageStorageManager(context)
    private val petId: Int? = savedStateHandle[PetDestinations.PET_ID_ARG]

    init {
        // Inicia a sincronização em tempo real quando o ViewModel é criado
        startRealtimeSync()
    }

    val allPets: StateFlow<List<Pet>> = repository.getAllPets()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val favoritePets: StateFlow<List<Pet>> = repository.getFavoritePets()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val uiState: StateFlow<Pet?> =
        if (petId != null) {
            repository.getPetStream(petId)
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = null
                )
        } else {
            MutableStateFlow(null)
        }

    fun insertPet(pet: Pet) {
        viewModelScope.launch { repository.insertPet(pet) }
    }

    fun updatePet(pet: Pet) {
        viewModelScope.launch { repository.updatePet(pet) }
    }

    fun deletePet(pet: Pet) {
        viewModelScope.launch {
            if (pet.imageUrl.startsWith(context.filesDir.absolutePath)) {
                imageStorageManager.deleteImageFromInternalStorage(pet.imageUrl)
            }
            repository.deletePet(pet)
        }
    }

    fun toggleFavoriteStatus(pet: Pet) {
        viewModelScope.launch {
            val updatedPet = pet.copy(isFavorite = !pet.isFavorite)
            repository.updatePet(updatedPet)
        }
    }

    fun deleteReminder(pet: Pet, reminder: Reminder) {
        viewModelScope.launch {
            val updatedReminders = pet.reminders.filterNot { it.id == reminder.id }
            val updatedPet = pet.copy(reminders = updatedReminders)
            repository.updatePet(updatedPet)
        }
    }

    fun toggleReminderCompleted(pet: Pet, reminder: Reminder) {
        viewModelScope.launch {
            val updatedReminders = pet.reminders.map {
                if (it.id == reminder.id) {
                    it.copy(isCompleted = !it.isCompleted)
                } else {
                    it
                }
            }
            val updatedPet = pet.copy(reminders = updatedReminders)
            repository.updatePet(updatedPet)
        }
    }

    private fun startRealtimeSync() {
        repository.startRealtimeSync()
    }

    override fun onCleared() {
        super.onCleared()
        // Para o listener quando o ViewModel é destruído para economizar recursos
        repository.stopRealtimeSync()
    }

    fun saveImageLocally(imageUri: Uri): String? {
        return imageStorageManager.saveImageToInternalStorage(imageUri)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as PetApplication
                val savedStateHandle = extras.createSavedStateHandle()
                return PetViewModel(application.repository, application.applicationContext, savedStateHandle) as T
            }
        }
    }
}