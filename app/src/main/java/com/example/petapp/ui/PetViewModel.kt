package com.example.petapp.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.petapp.PetApplication
import com.example.petapp.data.PetRepository
import com.example.petapp.data.model.Pet
import com.example.petapp.data.model.Reminder
import com.example.petapp.ui.navigation.PetDestinations
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PetViewModel(
    private val repository: PetRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // ## A CORREÇÃO FINAL ESTÁ AQUI ##
    // petId agora é um Int?, o que significa que ele PODE ser nulo.
    // Isso permite que o ViewModel seja usado por telas que não têm um ID (como a HomeScreen).
    private val petId: Int? = savedStateHandle[PetDestinations.PET_ID_ARG]

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

    // ## E A SEGUNDA PARTE DA CORREÇÃO ##
    // O uiState só tenta carregar um pet específico se o petId não for nulo.
    // Se for nulo, ele simplesmente continua nulo, sem causar erro.
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
        viewModelScope.launch { repository.deletePet(pet) }
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


    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as PetApplication
                val savedStateHandle = extras.createSavedStateHandle()
                return PetViewModel(application.repository, savedStateHandle) as T
            }
        }
    }
}