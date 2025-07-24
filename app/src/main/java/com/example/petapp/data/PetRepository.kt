package com.example.petapp.data

import com.example.petapp.data.model.Pet
import kotlinx.coroutines.flow.Flow

class PetRepository(private val petDao: PetDao) {
    fun getAllPets(): Flow<List<Pet>> = petDao.getAllPets()
    fun getPetById(id: Int): Flow<Pet> = petDao.getPetById(id)
    fun getFavoritePets(): Flow<List<Pet>> = petDao.getFavoritePets()
    suspend fun insertPet(pet: Pet) = petDao.insertPet(pet)
    suspend fun updatePet(pet: Pet) = petDao.updatePet(pet)
    suspend fun deletePet(pet: Pet) = petDao.deletePet(pet)
}