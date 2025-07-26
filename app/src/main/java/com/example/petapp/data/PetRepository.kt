package com.example.petapp.data

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.petapp.data.model.Pet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await

class PetRepository(
    private val petDao: PetDao,
    // Precisamos do contexto para iniciar o WorkManager
    private val context: Context
) {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val workManager = WorkManager.getInstance(context)

    private fun getCurrentUserId(): String? = auth.currentUser?.uid

    fun getAllPets(): Flow<List<Pet>> {
        val userId = getCurrentUserId()
        return if (userId != null) {
            petDao.getAllPets(userId)
        } else {
            flowOf(emptyList())
        }
    }

    fun getFavoritePets(): Flow<List<Pet>> {
        val userId = getCurrentUserId()
        return if (userId != null) {
            petDao.getFavoritePets(userId)
        } else {
            flowOf(emptyList())
        }
    }

    fun getPetStream(id: Int): Flow<Pet?> = petDao.getPetStream(id)

    suspend fun insertPet(pet: Pet) {
        getCurrentUserId()?.let {
            val petComDono = pet.copy(userId = it, needsSync = true)
            petDao.insertPet(petComDono)
            scheduleSync()
        }
    }

    suspend fun updatePet(pet: Pet) {
        getCurrentUserId()?.let {
            val petComDono = pet.copy(userId = it, needsSync = true)
            petDao.updatePet(petComDono)
            scheduleSync()
        }
    }

    suspend fun deletePet(pet: Pet) {
        petDao.deletePet(pet)
        deletePetFromFirestore(pet)
    }

    suspend fun syncPetsFromFirestore() {
        val userId = getCurrentUserId() ?: return
        try {
            val snapshot = firestore.collection("users").document(userId)
                .collection("pets").get().await()

            val firestorePets = snapshot.toObjects<Pet>()

            if (firestorePets.isNotEmpty()) {
                firestorePets.forEach { pet ->
                    // Ao sincronizar da nuvem para o local, marcamos como já sincronizado (needsSync = false)
                    petDao.insertPet(pet.copy(userId = userId, needsSync = false))
                }
                Log.d("PetRepository", "${firestorePets.size} pets sincronizados do Firestore para o Room.")
            }
        } catch (e: Exception) {
            Log.e("PetRepository", "Erro ao sincronizar pets do Firestore", e)
        }
    }

    private suspend fun deletePetFromFirestore(pet: Pet) {
        val userId = pet.userId
        if (userId.isBlank()) return
        try {
            firestore.collection("users").document(userId)
                .collection("pets").document(pet.id.toString())
                .delete().await()
            Log.d("PetRepository", "Pet deletado do Firestore com sucesso!")
        } catch (e: Exception) {
            Log.e("PetRepository", "Erro ao deletar pet do Firestore", e)
        }
    }

    private fun scheduleSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(constraints)
            .build()

        workManager.enqueue(syncRequest)
        Log.d("PetRepository", "Tarefa de sincronização agendada.")
    }
}