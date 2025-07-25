package com.example.petapp.data

import android.util.Log
import com.example.petapp.data.model.Pet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class PetRepository(private val petDao: PetDao) {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // Funções do Room
    fun getAllPets(): Flow<List<Pet>> = petDao.getAllPets()
    fun getFavoritePets(): Flow<List<Pet>> = petDao.getFavoritePets()
    fun getPetStream(id: Int): Flow<Pet?> = petDao.getPetStream(id)

    // Funções de escrita (agora sincronizadas)
    suspend fun insertPet(pet: Pet) {
        petDao.insertPet(pet)
        savePetToFirestore(pet)
    }

    suspend fun updatePet(pet: Pet) {
        petDao.updatePet(pet)
        savePetToFirestore(pet)
    }

    suspend fun deletePet(pet: Pet) {
        petDao.deletePet(pet)
        deletePetFromFirestore(pet)
    }

    // --- LÓGICA DE SINCRONIZAÇÃO DO FIRESTORE PARA O ROOM ---

    suspend fun syncPetsFromFirestore() {
        val userId = auth.currentUser?.uid ?: return
        try {
            val snapshot = firestore.collection("users").document(userId)
                .collection("pets").get().await()

            // Converte todos os documentos da coleção para uma lista de objetos Pet
            val firestorePets = snapshot.toObjects<Pet>()

            if (firestorePets.isNotEmpty()) {
                // Insere todos os pets baixados no banco de dados local
                // O OnConflictStrategy.REPLACE cuidará de adicionar novos e atualizar existentes
                firestorePets.forEach { pet ->
                    petDao.insertPet(pet)
                }
                Log.d("PetRepository", "${firestorePets.size} pets sincronizados do Firestore para o Room.")
            }
        } catch (e: Exception) {
            Log.e("PetRepository", "Erro ao sincronizar pets do Firestore", e)
        }
    }


    // Funções auxiliares privadas para o Firestore
    private suspend fun savePetToFirestore(pet: Pet) {
        val userId = auth.currentUser?.uid ?: return
        try {
            firestore.collection("users").document(userId)
                .collection("pets").document(pet.id.toString())
                .set(pet).await()
            Log.d("PetRepository", "Pet salvo no Firestore com sucesso!")
        } catch (e: Exception) {
            Log.e("PetRepository", "Erro ao salvar pet no Firestore", e)
        }
    }

    private suspend fun deletePetFromFirestore(pet: Pet) {
        val userId = auth.currentUser?.uid ?: return
        try {
            firestore.collection("users").document(userId)
                .collection("pets").document(pet.id.toString())
                .delete().await()
            Log.d("PetRepository", "Pet deletado do Firestore com sucesso!")
        } catch (e: Exception) {
            Log.e("PetRepository", "Erro ao deletar pet do Firestore", e)
        }
    }
}