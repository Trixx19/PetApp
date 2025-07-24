package com.example.petapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.petapp.data.PetRepository
import com.example.petapp.data.model.Pet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PetViewModel(private val repository: PetRepository) : ViewModel() {

    val allPets: StateFlow<List<Pet>> = repository.getAllPets()
        .stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(5000), initialValue = emptyList())

    val favoritePets: StateFlow<List<Pet>> = repository.getFavoritePets()
        .stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(5000), initialValue = emptyList())

    fun getPetById(id: Int): Flow<Pet> = repository.getPetById(id)

    fun insertPet(pet: Pet) = viewModelScope.launch { repository.insertPet(pet) }

    fun updatePet(pet: Pet) = viewModelScope.launch { repository.updatePet(pet) }

    fun deletePet(pet: Pet) = viewModelScope.launch { repository.deletePet(pet) }

    fun toggleFavorite(pet: Pet) = viewModelScope.launch {
        val updatedPet = pet.copy(isFavorite = !pet.isFavorite)
        repository.updatePet(updatedPet)
    }
}

class PetViewModelFactory(private val repository: PetRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PetViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PetViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}