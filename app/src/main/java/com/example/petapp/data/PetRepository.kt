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
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PetRepository(
    private val petDao: PetDao,
    private val context: Context
) {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val workManager = WorkManager.getInstance(context)
    private var firestoreListener: ListenerRegistration? = null

    private fun getCurrentUserId(): String? = auth.currentUser?.uid

    // --- NOVA FUNÇÃO PARA SINCRONIZAÇÃO EM TEMPO REAL ---
    fun startRealtimeSync() {
        val userId = getCurrentUserId() ?: return
        // Se já houver um listener, removemo-lo para evitar duplicados
        stopRealtimeSync()

        val query = firestore.collection("users").document(userId).collection("pets")

        firestoreListener = query.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e("PetRepository", "Erro no listener do Firestore", error)
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.isEmpty) {
                val firestorePets = snapshot.toObjects<Pet>()
                Log.d("PetRepository", "Dados recebidos em tempo real: ${firestorePets.size} pets.")
                // Usamos um CoroutineScope para inserir os dados numa thread de fundo
                CoroutineScope(Dispatchers.IO).launch {
                    firestorePets.forEach { pet ->
                        // Inserimos no Room, marcando como já sincronizado
                        petDao.insertPet(pet.copy(userId = userId, needsSync = false))
                    }
                }
            }
        }
    }

    // Função para parar o listener quando não for mais necessário
    fun stopRealtimeSync() {
        firestoreListener?.remove()
        firestoreListener = null
    }

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