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

    @Query("SELECT * from pets WHERE userId = :userId ORDER BY name ASC")
    fun getAllPets(userId: String): Flow<List<Pet>>

    @Query("SELECT * from pets WHERE id = :id")
    fun getPetStream(id: Int): Flow<Pet?>

    @Query("SELECT * from pets WHERE userId = :userId AND isFavorite = 1 ORDER BY name ASC")
    fun getFavoritePets(userId: String): Flow<List<Pet>>

    // NOVA QUERY PARA O WORKMANAGER
    @Query("SELECT * FROM pets WHERE needsSync = 1")
    suspend fun getPetsNeedingSync(): List<Pet>
}