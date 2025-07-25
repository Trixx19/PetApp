package com.example.petapp.data

import android.util.Log
import com.example.petapp.data.model.Pet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class PetRepository(private val petDao: PetDao) {

    // Instâncias do Firebase
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // Funções que já existiam (para o Room)
    fun getAllPets(): Flow<List<Pet>> = petDao.getAllPets()
    fun getFavoritePets(): Flow<List<Pet>> = petDao.getFavoritePets()
    fun getPetStream(id: Int): Flow<Pet?> = petDao.getPetStream(id)

    // Função modificada para salvar em ambos os locais
    suspend fun insertPet(pet: Pet) {
        // Salva localmente primeiro
        petDao.insertPet(pet)
        // Em seguida, salva na nuvem
        savePetToFirestore(pet)
    }

    // Função modificada para atualizar em ambos os locais
    suspend fun updatePet(pet: Pet) {
        petDao.updatePet(pet)
        savePetToFirestore(pet) // A mesma função de salvar serve para atualizar
    }

    // Função modificada para deletar de ambos os locais
    suspend fun deletePet(pet: Pet) {
        petDao.deletePet(pet)
        deletePetFromFirestore(pet)
    }

    // --- NOVAS FUNÇÕES PARA O FIRESTORE ---

    private suspend fun savePetToFirestore(pet: Pet) {
        val userId = auth.currentUser?.uid ?: return // Não faz nada se não houver usuário logado
        try {
            // Usa o ID do Room como ID do documento no Firestore para manter a consistência
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