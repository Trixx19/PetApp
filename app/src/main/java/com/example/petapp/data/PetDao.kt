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

    @Query("SELECT * from pets ORDER BY name ASC")
    fun getAllPets(): Flow<List<Pet>>

    @Query("SELECT * from pets WHERE id = :id")
    fun getPetStream(id: Int): Flow<Pet?>

    @Query("SELECT * from pets WHERE isFavorite = 1 ORDER BY name ASC")
    fun getFavoritePets(): Flow<List<Pet>>
}
/*package com.example.petapp.data

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

    @Query("SELECT * from pets ORDER BY name ASC")
    fun getAllPets(): Flow<List<Pet>>

    @Query("SELECT * from pets WHERE id = :id")
    fun getPetStream(id: Int): Flow<Pet?> // Modificado para ser anulável

    @Query("SELECT * from pets WHERE isFavorite = 1 ORDER BY name ASC")
    fun getFavoritePets(): Flow<List<Pet>>

    @Query("DELETE FROM pets")
    suspend fun deleteAllPets()
}*/