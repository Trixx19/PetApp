package com.example.petapp.data

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class SyncWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    // Acesso direto ao DAO e Firestore para a tarefa em segundo plano
    private val petDao = PetDatabase.getDatabase(appContext).petDao()
    private val firestore = FirebaseFirestore.getInstance()

    override suspend fun doWork(): Result {
        try {
            Log.d("SyncWorker", "Iniciando a tarefa de sincronização.")

            // 1. Obter todos os pets que precisam ser sincronizados
            val petsToSync = petDao.getPetsNeedingSync()
            if (petsToSync.isEmpty()) {
                Log.d("SyncWorker", "Nenhum pet para sincronizar.")
                return Result.success()
            }

            Log.d("SyncWorker", "Sincronizando ${petsToSync.size} pets.")

            // 2. Iterar e salvar cada pet no Firestore
            for (pet in petsToSync) {
                if (pet.userId.isBlank()) continue

                firestore.collection("users").document(pet.userId)
                    .collection("pets").document(pet.id.toString())
                    .set(pet).await()

                // 3. Após o sucesso, atualizar o pet localmente para não ser sincronizado de novo
                petDao.updatePet(pet.copy(needsSync = false))
            }

            Log.d("SyncWorker", "Sincronização concluída com sucesso.")
            return Result.success()

        } catch (e: Exception) {
            Log.e("SyncWorker", "Erro durante a sincronização.", e)
            // Se algo der errado, podemos tentar novamente mais tarde
            return Result.retry()
        }
    }
}