package com.example.petapp.data

import androidx.room.*
import com.example.petapp.data.model.Pet
import kotlinx.coroutines.flow.Flow

@Dao
interface PetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPet(pet: Pet)

    @Update
    suspend fun updatePet(pet: Pet)

    @Delete
    suspend fun deletePet(pet: Pet)

    @Query("SELECT * FROM pets ORDER BY name ASC")
    fun getAllPets(): Flow<List<Pet>>

    @Query("SELECT * FROM pets WHERE id = :petId")
    fun getPetById(petId: Int): Flow<Pet>

    @Query("SELECT * FROM pets WHERE isFavorite = 1 ORDER BY name ASC")
    fun getFavoritePets(): Flow<List<Pet>>
}