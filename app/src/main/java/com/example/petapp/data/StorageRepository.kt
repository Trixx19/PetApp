package com.example.petapp.data

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID

class StorageRepository {

    private val storage = FirebaseStorage.getInstance().reference
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    // Função para fazer upload de uma imagem e retornar a URL de download
    suspend fun uploadPetImage(imageUri: Uri): String? {
        if (userId == null) {
            Log.e("StorageRepository", "Usuário não logado, upload cancelado.")
            return null
        }

        // Cria um nome de arquivo único para evitar sobreposições
        val fileName = "pet_images/${userId}/${UUID.randomUUID()}.jpg"
        val imageRef = storage.child(fileName)

        return try {
            // Faz o upload do arquivo
            imageRef.putFile(imageUri).await()
            // Após o upload, obtém a URL de download permanente
            val downloadUrl = imageRef.downloadUrl.await().toString()
            Log.d("StorageRepository", "Upload bem-sucedido: $downloadUrl")
            downloadUrl
        } catch (e: Exception) {
            Log.e("StorageRepository", "Falha no upload da imagem", e)
            null
        }
    }
}